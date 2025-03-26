package ru.denisov.NauJava.entity;

import jakarta.persistence.*;
import ru.denisov.NauJava.enums.OrganizationType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private OrganizationType type;

    @Column(name = "industry")
    private String industry;
    @Column(name = "website")
    private String website;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Deal> deals = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, OrganizationType type, String industry, String website,
                        Timestamp createdAt, List<Contact> contacts, List<Address> addresses, List<Deal> deals) {
        this.name = name;
        this.type = type;
        this.industry = industry;
        this.website = website;
        this.contacts = contacts;
        this.addresses = addresses;
        this.deals = deals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
