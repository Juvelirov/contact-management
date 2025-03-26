package ru.denisov.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.entity.Organization;
import ru.denisov.NauJava.repository.ContactRepository;
import ru.denisov.NauJava.repository.OrganizationRepository;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository, ContactRepository contactRepository) {
        this.organizationRepository = organizationRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    @Transactional
    public void createOrganizationWithContact(Organization organization, Contact contact) {
        if (contact.getFirstname() == null || contact.getSurname() == null) {
            throw new RuntimeException("Имя или фамилия контакта не могут быть пустыми");
        }
        organizationRepository.save(organization);
        contact.setOrganizationId(organization);
        contactRepository.save(contact);
    }
}
