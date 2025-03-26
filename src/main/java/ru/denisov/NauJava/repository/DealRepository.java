package ru.denisov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.denisov.NauJava.entity.Deal;

public interface DealRepository extends CrudRepository<Deal, Integer> {
}
