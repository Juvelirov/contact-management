package ru.denisov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.denisov.NauJava.entity.Organization;

@RepositoryRestResource
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {

}
