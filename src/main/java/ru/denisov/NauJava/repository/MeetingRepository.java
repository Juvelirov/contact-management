package ru.denisov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.denisov.NauJava.entity.Meeting;

public interface MeetingRepository extends CrudRepository<Meeting, Integer> {
}
