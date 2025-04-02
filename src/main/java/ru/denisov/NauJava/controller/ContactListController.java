package ru.denisov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.repository.ContactRepository;

import java.util.List;

/**
 * Данный класс отвечает за отображение списка контактов на странице html.
 * Он получает все контакты из базы данных и передает их на страницу для отображения.
 */
@Controller
public class ContactListController {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactListController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Обрабатывает GET запрос к странице "/contactsView".
     *
     * @param model используется для передачи данных на веб-страницу
     * @return имя страницы, на которую будут переданы данные
     */
    @GetMapping("/contactsView")
    public String showContacts(Model model) {
        List<Contact> contacts = (List<Contact>) contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "contactList";
    }
}
