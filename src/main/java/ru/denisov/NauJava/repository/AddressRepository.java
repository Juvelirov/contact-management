package ru.denisov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.denisov.NauJava.entity.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {
}
