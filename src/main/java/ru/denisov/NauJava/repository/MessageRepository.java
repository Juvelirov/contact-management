package ru.denisov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.denisov.NauJava.entity.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
