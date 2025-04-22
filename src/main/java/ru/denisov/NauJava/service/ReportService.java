package ru.denisov.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.entity.Report;
import ru.denisov.NauJava.enums.ReportStatus;
import ru.denisov.NauJava.other.TaskResult;
import ru.denisov.NauJava.repository.ReportRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ContactService contactService;
    private final UserService userService;

    @Autowired
    public ReportService(ReportRepository reportRepository, ContactService contactService, UserService userService){
        this.reportRepository = reportRepository;
        this.contactService = contactService;
        this.userService = userService;
    }

    public Long createReport() {
        Report report = new Report();
        report = reportRepository.save(report);
        return report.getId();
    }

    // Получение содержимого отчёта по ИД
    public String getReportContent(Long reportId) {
        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isPresent()) {
            return report.get().getContent();
        } else {
            return "Отчет не найден";
        }
    }

    public void generateReportAsync(Long reportId) {
        CompletableFuture.runAsync(() -> {
            try {
                generateReport(reportId);
            } catch (Exception e) {
                handleReportError(reportId, e);
            }
        });
    }

    private void generateReport(Long reportId) throws ExecutionException, InterruptedException {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Отчёт не найден"));

        try {
            long startTime = System.currentTimeMillis();

            CompletableFuture<TaskResult<Long>> usersFuture = CompletableFuture.supplyAsync(() -> {
                long taskStart = System.currentTimeMillis();
                long count = userService.countUsers();
                return new TaskResult<>(count, System.currentTimeMillis() - taskStart);
            });

            CompletableFuture<TaskResult<List<Contact>>> contactsFuture = CompletableFuture.supplyAsync(() -> {
                long taskStart = System.currentTimeMillis();
                List<Contact> entities = contactService.getAllEntities();
                return new TaskResult<>(entities, System.currentTimeMillis() - taskStart);
            });

            CompletableFuture.allOf(usersFuture, contactsFuture).join();

            TaskResult<Long> userCountResult = usersFuture.get();
            TaskResult<List<Contact>> entitiesResult = contactsFuture.get();

            String htmlReport = buildHtmlReport(
                    userCountResult.getResult(),
                    entitiesResult.getResult(),
                    userCountResult.getExecutionTime(),
                    entitiesResult.getExecutionTime(),
                    System.currentTimeMillis() - startTime
            );

            report.setContent(htmlReport);
            report.setStatus(ReportStatus.COMPLETED);
            report.setCompletedAt(LocalDateTime.now());

        } catch (Exception exception) {
            report.setStatus(ReportStatus.ERROR);
            report.setContent("Ошибка " + exception.getMessage());
            throw exception;
        } finally {
            reportRepository.save(report);
        }
    }

    // Для thymeleaf
    @Autowired
    private TemplateEngine templateEngine;

    private String buildHtmlReport(long userCount, List<Contact> entities,
                                   long userCountTime, long entitiesTime, long totalTime) {
        Context context = new Context();
        context.setVariable("userCount", userCount);
        context.setVariable("entities", entities);
        context.setVariable("userCountTime", userCountTime);
        context.setVariable("entitiesTime", entitiesTime);
        context.setVariable("totalTime", totalTime);

        return templateEngine.process("reportTemplate", context);
    }

    private void handleReportError(Long reportId, Exception e) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Отчет не найден"));
        report.setStatus(ReportStatus.ERROR);
        report.setContent("Ошибка при формировании отчета: " + e.getMessage());
        reportRepository.save(report);
    }
}
