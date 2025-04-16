package ru.denisov.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.denisov.NauJava.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
