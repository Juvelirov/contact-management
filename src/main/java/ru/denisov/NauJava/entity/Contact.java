package ru.denisov.NauJava.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_contacts")
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



    public Contact() {}

    public Contact(String firstname, String surname, String position, String phoneNumber, String email, Organization organizationId) {
        this.firstname = firstname;
        this.surname = surname;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.organization = organizationId;
    }

    // Геттеры и сеттеры
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", position='" + position + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", organizationId=" + organization +
                '}';
    }

    public Organization getOrganizationId() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
