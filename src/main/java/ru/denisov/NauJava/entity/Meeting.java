package ru.denisov.NauJava.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="date")
    private LocalDate date;
    @Column(name="time")
    private LocalTime time;
    @Column(name="location")
    private String location;

    @Column(name="purpose")
    private String purpose;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToMany
    @JoinTable(
            name = "tbl_meeting_contacts",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<Contact> contacts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tbl_meeting_deals",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "deal_id")
    )
    private List<Deal> deals = new ArrayList<>();

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", purpose='" + purpose + '\'' +
                ", createdAt=" + createdAt +
                ", contacts=" + contacts +
                ", deals=" + deals +
                '}';
    }
}
