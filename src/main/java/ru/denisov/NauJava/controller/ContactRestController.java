package ru.denisov.NauJava.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.repository.ContactRepository;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactRestController {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactRestController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Поиск контактов по имени и фамилии.
     *
     * @param firstname имя
     * @param lastname фамилия
     * @return список контактов при успехе, сообщение об ошибке при пустых параметрах
     */
    @GetMapping("/findByName")
    public ResponseEntity<?> findByFullName(
            @RequestParam @NotBlank String firstname,
            @RequestParam @NotBlank String lastname) {

        if (firstname.isBlank() || lastname.isBlank()) {
            return ResponseEntity.badRequest().body("Имя и фамилия обязательны к заполнению!");
        }

        return ResponseEntity.ok(
                contactRepository.findByFirstnameAndLastname(firstname, lastname)
        );
    }

    /**
     * Поиск контактов по наименованию организации.
     *
     * @param organizationName название организации
     * @return список контактов при успехе, сообщение об ошибке если организация не найдена в бд
     */
    @GetMapping("/findByOrganization")
    public ResponseEntity<?> findByOrganization(
            @RequestParam @NotBlank String organizationName) {

        if (organizationName.isBlank()) {
            return ResponseEntity.badRequest().body("Название организации обязательно к заполнению!");
        }

        List<Contact> contacts = contactRepository.findContactsByOrganizationName(organizationName);

        if (contacts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Контакты не найдены для этой организации.");
        }

        return ResponseEntity.ok(contacts);
    }
}
