package ru.denisov.NauJava.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.denisov.NauJava.enums.ReportStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_report")
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = ReportStatus.CREATED;
    }
}
