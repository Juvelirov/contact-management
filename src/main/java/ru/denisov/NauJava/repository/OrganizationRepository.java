package ru.denisov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;

import ru.denisov.NauJava.entity.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Integer> {

}
