package ru.denisov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.denisov.NauJava.service.ReportService;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Создает новый отчёт и запускает его генерацию.
     * @return id созданного отчета
     */
    @PostMapping
    public Long createReport() {
        Long reportId = reportService.createReport();
        reportService.generateReportAsync(reportId);
        return reportId;
    }

    /**
     * Получает контент отчёта по ИД.
     * @param id id отчёта
     * @return контент отчёта
     */
    @GetMapping("/{id}")
    public String getReport(@PathVariable Long id){
        return reportService.getReportContent(id);
    }
}
