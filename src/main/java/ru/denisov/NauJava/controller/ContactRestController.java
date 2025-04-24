package ru.denisov.NauJava.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.denisov.NauJava.dto.ContactDto;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.exception.NotFoundException;
import ru.denisov.NauJava.service.ContactService;
import java.util.List;

@RestController
@RequestMapping("api/v1/contacts")
@Slf4j
public class ContactRestController {

    private final ContactService contactService;

    @Autowired
    public ContactRestController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody ContactDto contactDto) {
        log.info("POST /api/v1/contacts - create contact");
        Contact createdContact = contactService.createContact(contactDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        log.info("GET /api/v1/contacts - get all contacts");
        List<Contact> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable Integer id) {
        log.info("GET /api/v1/contacts/{} - get contact by id", id);
        try {
            Contact contact = contactService.getContactById(id);
            return ResponseEntity.ok(contact);
        } catch (NotFoundException e) {
            log.error("Contact not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Integer id, @Valid @RequestBody ContactDto contactDto) {
        log.info("PUT /api/v1/contacts/{} - update contact", id);
        try {
            Contact updatedContact = contactService.updateContact(id, contactDto);
            return ResponseEntity.ok(updatedContact);
        } catch (NotFoundException e) {
            log.error("Contact not found for update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Integer id) {
        log.info("DELETE /api/v1/contacts/{} - delete contact", id);
        try {
            contactService.deleteContact(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error("Contact not found for deletion: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}