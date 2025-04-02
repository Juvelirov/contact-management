package ru.denisov.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.denisov.NauJava.entity.Contact;

import java.util.List;

@RepositoryRestResource
public interface ContactRepository extends CrudRepository<Contact, Integer> {
    // Объявление магии
    List<Contact> findByFirstnameAndLastname(String firstname, String lastname);
    // jpql
    @Query("SELECT c FROM Contact c JOIN c.organization o WHERE o.name = :organizationName")
    List<Contact> findContactsByOrganizationName(@Param("organizationName") String organizationName);
}
