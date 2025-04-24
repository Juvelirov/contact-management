package ru.denisov.NauJava.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_contacts")
@Data
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String surname;

    @Column(name = "position")
    private String position;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name="organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    @ManyToMany(mappedBy = "contacts")
    private List<Meeting> meetings = new ArrayList<>();

    public Contact(String firstname, String surname, String position, String phoneNumber, String email, Organization organizationId) {
        this.firstname = firstname;
        this.surname = surname;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.organization = organizationId;
    }
}
