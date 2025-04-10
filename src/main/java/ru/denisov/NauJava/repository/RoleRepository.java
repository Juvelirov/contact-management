package ru.denisov.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.denisov.NauJava.entity.Role;
import ru.denisov.NauJava.entity.enums.Roles;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}