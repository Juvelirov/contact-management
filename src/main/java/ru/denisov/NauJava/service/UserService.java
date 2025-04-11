package ru.denisov.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.denisov.NauJava.entity.UserEntity;
import ru.denisov.NauJava.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);

    }

    public void addUser(UserEntity user) {
        userRepository.save(user);
    }
}
