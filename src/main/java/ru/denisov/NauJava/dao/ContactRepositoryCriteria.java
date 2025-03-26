package ru.denisov.NauJava.dao;

import java.util.List;
import ru.denisov.NauJava.entity.Contact;


public interface ContactRepositoryCriteria {

    List<Contact> findByFirstnameAndLastname(String firstname, String lastname);

    List<Contact> findContactsByOrganizationName(String organizationName);
}
