package ru.denisov.NauJava.dao;

import java.util.List;
import ru.denisov.NauJava.entity.Contact;


public interface ContactRepositoryCriteria {

    /**
     * Ищет контакты по имени и фамилии.
     *
     * @param firstname имя
     * @param lastname фамилия
     * @return список контактов, соответствующих указанному имени и фамилии
     */
    List<Contact> findByFirstnameAndLastname(String firstname, String lastname);

    /**
     * Находит контакты по названию организации.
     *
     * @param organizationName название организации
     * @return список контактов
     */
    List<Contact> findContactsByOrganizationName(String organizationName);
}
