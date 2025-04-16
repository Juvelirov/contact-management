package ru.denisov.NauJava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.denisov.NauJava.entity.User;
import ru.denisov.NauJava.service.UserService;
import java.util.Optional;

// С Security не стал внедрять пользователя, поскольку не разобрался ещё с ошибкой. Будем считать это заглушкой для отчёта.
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получить пользователя по ИД.
     * @param id id пользователя
     * @return ответ с пользователем, если найден, иначе 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Создать пользователя.
     * @param user данные пользователя из запроса
     * @return ответ с созданным пользователем
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
