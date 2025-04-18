package ru.denisov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.denisov.NauJava.entity.Role;
import ru.denisov.NauJava.entity.UserEntity;
import ru.denisov.NauJava.entity.enums.Roles;
import ru.denisov.NauJava.repository.RoleRepository;
import ru.denisov.NauJava.service.UserService;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // Добавление дефолтной роли
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName(Roles.ROLE_USER);
        roles.add(role);
        user.setRoles(roles);

        userService.addUser(user);

        return "redirect:/login";

    }
}
