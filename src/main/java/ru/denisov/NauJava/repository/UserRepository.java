package ru.denisov.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.denisov.NauJava.entity.UserEntity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
