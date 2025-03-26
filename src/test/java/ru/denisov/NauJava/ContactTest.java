package ru.denisov.NauJava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.entity.Organization;
import ru.denisov.NauJava.repository.ContactRepository;
import ru.denisov.NauJava.repository.OrganizationRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactTest {

    private final ContactRepository contactRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    ContactTest(ContactRepository contactRepository, OrganizationRepository organizationRepository) {
        this.contactRepository = contactRepository;
        this.organizationRepository = organizationRepository;
    }

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        organizationRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
        organizationRepository.deleteAll();
    }

    @Test
    void testFindByFirstnameAndLastname() {
        String firstname = UUID.randomUUID().toString();
        String lastname = UUID.randomUUID().toString();

        Organization organization = new Organization("convex", null, "svyaz", "convex.com", null, null, null, null);
        organizationRepository.save(organization);

        Contact contact = new Contact(firstname, lastname, "manager", "88005553535", "convexmngr@mail.ru", organization);
        contactRepository.save(contact);

        List<Contact> foundContacts = contactRepository.findByFirstnameAndLastname(firstname, lastname);

        assertNotNull(foundContacts);
        assertEquals(1, foundContacts.size());
        assertEquals(contact.getId(), foundContacts.get(0).getId());
        assertEquals(firstname, foundContacts.get(0).getFirstname());
        assertEquals(lastname, foundContacts.get(0).getSurname());
    }

    @Test
    void testFindByFirstnameAndLastname_NoResults() {
        List<Contact> foundContacts = contactRepository.findByFirstnameAndLastname("Vasya", "Petya");

        assertTrue(foundContacts.isEmpty());
    }

    @Test
    void testFindContactsByOrganizationName() {
        String organizationName = UUID.randomUUID().toString();

        Organization organization = new Organization(organizationName, null, "metallurgia", "ugmk.com", null, null, null, null);
        organizationRepository.save(organization);

        Contact contact = new Contact("Alexander", "Petrov", "hr", "88005553535", "petrov@mail.ru", organization);
        contactRepository.save(contact);

        List<Contact> foundContacts = contactRepository.findContactsByOrganizationName(organizationName);

        assertNotNull(foundContacts);
        assertEquals(1, foundContacts.size());
        assertEquals(contact.getId(), foundContacts.get(0).getId());
        assertEquals(organizationName, foundContacts.get(0).getOrganizationId().getName());
    }

    @Test
    void testFindContactsByOrganizationName_NoResults() {
        List<Contact> foundContacts = contactRepository.findContactsByOrganizationName("Не существует");

        assertTrue(foundContacts.isEmpty());
    }
}
