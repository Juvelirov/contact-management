package ru.denisov.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.repository.ContactRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Для отчёта
    public List<Contact> getAllEntities() {
        return (List<Contact>) contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Integer id) {
        return contactRepository.findById(id);
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public void deleteContactById(Integer id) {
        contactRepository.deleteById(id);
    }

    public List<Contact> findByFirstnameAndLastname(String firstname, String lastname) {
        return contactRepository.findByFirstnameAndLastname(firstname, lastname);
    }

    public List<Contact> findContactsByOrganizationName(String organizationName) {
        return contactRepository.findContactsByOrganizationName(organizationName);
    }
}