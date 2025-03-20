package ru.denisov.NauJava.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.denisov.NauJava.Entity.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Репо для управления контактами.
 * Представляет собой имитацию слоя для работы с БД, где контакты хранятся в List.
 *
 * @author Artem Denisov
 */
@Repository
public class ContactRepository implements  CrudRepository<Contact, Long>{

    private final List<Contact> contactContainer;

    @Autowired
    public ContactRepository(List<Contact> contactContainer){
        this.contactContainer = contactContainer;
    }

    /**
     * Создает новый контакт в репозитории. Проводит базовую валидацию сущности и проверяет уникальность идентификатора и номера телефона.
     *
     * @param entity Контакт для добавления.
     * @throws IllegalArgumentException Если контакт с таким идентификатором или номером телефона уже существует.
     */
    @Override
    public void create(Contact entity) {
        validateContact(entity); // Проводим базовую валидацию сущности и её id.
        if (isContactExists(entity.getId())) {
            throw new IllegalArgumentException("Контакт с таким идентификатором уже существует!");
        }
        if (isContactExistsByNumber(entity.getPhoneNumber())) {
            throw new IllegalArgumentException("Контакт с таким номером телефона уже существует!");
        }
        contactContainer.add(entity); // Если всё хорошо - добавляем контакт в нашу иммитацию БД.
    }

    /**
     * Возвращает контакт по его уникальному идентификатору.
     *
     * @param id Идентификатор контакта.
     * @return Контакт с указанным идентификатором или null, если не найден.
     */
    @Override
    public Contact read(Long id) {
        for (Contact contact : contactContainer) {
            if (contact.getId().equals(id)) {
                return contact;
            }
        }
        return null;
    }

    /**
     * Возвращает контакт по его номеру телефона.
     *
     * @param number Номер телефона контакта.
     * @return Контакт с указанным номером телефона или null, если не найден.
     */
    @Override
    public Contact read(String number) {
        for (Contact contact : contactContainer) {
            if (contact.getPhoneNumber().equals(number)) {
                return contact;
            }
        }
        return null;
    }

    /**
     * Возвращает список всех контактов, хранящихся в бд.
     *
     * @return Список контактов.
     */
    public List<Contact> readAll() {
        return new ArrayList<>(contactContainer);
    }

    /**
     * Обновляет существующий контакт в репозитории. Проводит базовую валидацию сущности и проверяет существование контакта по идентификатору.
     *
     * @param entity Контакт с обновленными данными.
     * @throws IllegalArgumentException Если контакт для обновления не найден.
     */
    @Override
    public void update(Contact entity) {
        validateContact(entity);
        Contact existingContact = read(entity.getId());
        if (existingContact == null) {
            throw new IllegalArgumentException("Контакт для обновления не найден!");
        }
        existingContact.setPhoneNumber(entity.getPhoneNumber());
    }

    /**
     * Удаляет контакт из репозитория по его уникальному идентификатору.
     *
     * @param id Идентификатор контакта для удаления.
     */
    @Override
    public void delete(Long id) {
        for (int i = 0; i < contactContainer.size(); i++) {
            if (contactContainer.get(i).getId().equals(id)) {
                contactContainer.remove(i);
                break;
            }
        }
    }

    // Методы валидации
    private void validateContact(Contact entity) {
        if (entity == null) {
            throw new NullPointerException("Контакт не может быть null!");
        }
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Идентификатор контакта не может быть null!");
        }
    }
    private boolean isContactExists(Long id) {
        for (Contact contact : contactContainer) {
            if (contact.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean isContactExistsByNumber(String number) {
        for (Contact contact : contactContainer) {
            if (contact.getPhoneNumber().equals(number)) {
                return true;
            }
        }
        return false;
    }
}
