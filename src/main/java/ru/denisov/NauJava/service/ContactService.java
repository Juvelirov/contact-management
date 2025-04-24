package ru.denisov.NauJava.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.entity.Organization;
import ru.denisov.NauJava.exception.NotFoundException;
import ru.denisov.NauJava.repository.ContactRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.denisov.NauJava.dto.ContactDto;
import ru.denisov.NauJava.repository.OrganizationRepository;

@Service
@Slf4j
public class ContactService {

    private final ContactRepository contactRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, OrganizationRepository organizationRepository) {
        this.contactRepository = contactRepository;
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public Contact createContact(ContactDto dto) {
        log.info("create new contact with firstname: {}, surname: {}", dto.getFirstname(), dto.getSurname());
        Contact contact = new Contact();
        contact.setFirstname(dto.getFirstname());
        contact.setSurname(dto.getSurname());
        contact.setPosition(dto.getPosition());
        contact.setPhoneNumber(dto.getPhoneNumber());
        contact.setEmail(dto.getEmail());

        if (dto.getOrganizationId() != null) {
            Organization org = organizationRepository.findById(dto.getOrganizationId())
                    .orElseThrow(() -> {
                        log.error("organization with id {} not found", dto.getOrganizationId());
                        return new NotFoundException("organization not found");
                    });
            contact.setOrganization(org);
            log.info("associate contact with organization id {}", dto.getOrganizationId());
        }

        contact.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return contactRepository.save(contact);
    }

    @Transactional(readOnly = true)
    public List<Contact> getAllContacts() {
        log.info("Fetching all contacts");
        Iterable<Contact> contactsIterable = contactRepository.findAll();
        List<Contact> contacts = new ArrayList<>();
        for (Contact c : contactsIterable) {
            contacts.add(c);
        }
        return contacts;
    }

    @Transactional(readOnly = true)
    public Contact getContactById(Integer id) throws NotFoundException {
        log.info("Fetching contact with id {}", id);
        Optional<Contact> contactOpt = contactRepository.findById(id);
        if (!contactOpt.isPresent()) {
            log.error("Contact with id {} not found", id);
            throw new NotFoundException("Contact with id " + id + " not found");
        }
        return contactOpt.get();
    }

    @Transactional
    public Contact updateContact(Integer id, ContactDto dto) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact with id " + id + " not found"));

        existingContact.setFirstname(dto.getFirstname());
        existingContact.setSurname(dto.getSurname());
        existingContact.setPosition(dto.getPosition());
        existingContact.setPhoneNumber(dto.getPhoneNumber());
        existingContact.setEmail(dto.getEmail());

        if (dto.getOrganizationId() != null) {
            Organization org = organizationRepository.findById(dto.getOrganizationId())
                    .orElseThrow(() -> new NotFoundException("Organization not found"));
            existingContact.setOrganization(org);
        }

        return contactRepository.save(existingContact);
    }

    @Transactional
    public void deleteContact(Integer id) throws NotFoundException {
        log.info("Deleting contact with id {}", id);
        if (!contactRepository.existsById(id)) {
            log.error("Contact with id {} not found for deletion", id);
            throw new NotFoundException("Contact with id " + id + " not found");
        }
        contactRepository.deleteById(id);
    }
}
