/*
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
import ru.denisov.NauJava.service.OrganizationService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrganizationServiceTest {

    private final OrganizationService organizationService;
    private final OrganizationRepository organizationRepository;
    private final ContactRepository contactRepository;

    @Autowired
    OrganizationServiceTest(OrganizationService organizationService, OrganizationRepository organizationRepository, ContactRepository contactRepository) {
        this.organizationService = organizationService;
        this.organizationRepository = organizationRepository;
        this.contactRepository = contactRepository;
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
    void testCreateOrganizationWithContact() {
        Organization organization = new Organization("org", null, "ind", "pochta@mail.ru", null, null, null, null);
        Contact contact = new Contact("Artem", "Denisov", "Not Manager", "88005553535", "tema@mail.ru", null);

        organizationService.createOrganizationWithContact(organization, contact);

        assertNotNull(organizationRepository.findById(1).orElse(null));
        assertNotNull(contactRepository.findById(1).orElse(null));
        assertEquals(organization, contactRepository.findById(1).orElse(null).getOrganizationId());
    }

    @Test
    void testCreateOrganizationWithContactFailure() {
        Organization organization = new Organization("Test", null, "Test", "test.com", null, null, null, null);
        Contact contact = new Contact(null, null, "Manager", "880055535535", "fawfawf@mail.ru", null);

        assertThrows(Exception.class, () -> organizationService.createOrganizationWithContact(organization, contact));

        // Проверки после отмены транзакции
        assertTrue(organizationRepository.findAll().iterator().hasNext() == false);
        assertTrue(contactRepository.findAll().iterator().hasNext() == false);
    }
}

*/
