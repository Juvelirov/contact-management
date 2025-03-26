package ru.denisov.NauJava.service;

import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.entity.Organization;

public interface OrganizationService {
   // Организация не может существовать без контакта, как и контакт без организации.
    void createOrganizationWithContact(Organization organization, Contact contact);
}
