package ru.denisov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.denisov.NauJava.entity.Address;

@RepositoryRestResource
public interface AddressRepository extends CrudRepository<Address, Integer> {

}
