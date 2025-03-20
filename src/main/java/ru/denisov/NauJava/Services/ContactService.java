package ru.denisov.NauJava.Services;

import ru.denisov.NauJava.Entity.Contact;

import java.util.List;

public interface ContactService {
    void createContact(Long id, String firstname, String surname, String phone);
    Contact findById(Long id);
    Contact findByNumber(String number);
    void deleteById(Long id);
    void updatePhoneNumber(Long id, String newNumber);
    List<Contact> getAllContacts();
}
