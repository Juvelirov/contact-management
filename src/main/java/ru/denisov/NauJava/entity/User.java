package ru.denisov.NauJava.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
    }
}
