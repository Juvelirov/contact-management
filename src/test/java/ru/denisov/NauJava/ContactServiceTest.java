package ru.denisov.NauJava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.denisov.NauJava.dto.ContactDto;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.exception.NotFoundException;
import ru.denisov.NauJava.repository.ContactRepository;
import ru.denisov.NauJava.repository.OrganizationRepository;
import ru.denisov.NauJava.service.ContactService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    private ContactService contactService;

    private ContactDto defaultGlobalDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contactService = new ContactService(contactRepository, organizationRepository);
        defaultGlobalDto = new ContactDto();
        defaultGlobalDto.setFirstname("Александр");
        defaultGlobalDto.setSurname("Петров");
        defaultGlobalDto.setPosition("Разработчик");
        defaultGlobalDto.setPhoneNumber("+79021889755");
        defaultGlobalDto.setEmail("sanyapetrov123@mail.ru");
    }

    @Test
    void testCreateContactWithoutOrganization() {
        defaultGlobalDto.setOrganizationId(null);

        when(contactRepository.save(any(Contact.class))).thenAnswer(invocation -> {
            Contact contact = invocation.getArgument(0);
            contact.setId(1);
            return contact;
        });

        Contact result = contactService.createContact(defaultGlobalDto);

        assertNotNull(result);
        assertEquals("Александр", result.getFirstname());
        assertEquals("Петров", result.getSurname());
        assertNull(result.getOrganization());
        assertEquals(1, result.getId());
    }

    @Test
    void testCreateContactWithOrganizationNotFound() {
        defaultGlobalDto.setOrganizationId(99);
        when(organizationRepository.findById(99)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            contactService.createContact(defaultGlobalDto);
        });

        assertEquals("organization not found", exception.getMessage());

        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void testGetContactById() throws NotFoundException {
        Contact contact = new Contact();
        contact.setId(10);
        contact.setFirstname("Artem");
        contact.setSurname("Denisov");

        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));

        Contact result = contactService.getContactById(1);

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals("Artem", result.getFirstname());
        assertEquals("Denisov", result.getSurname());

        verify(contactRepository, times(1)).findById(1);
    }
}