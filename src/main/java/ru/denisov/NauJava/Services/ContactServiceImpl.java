package ru.denisov.NauJava.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denisov.NauJava.Entity.Contact;
import ru.denisov.NauJava.Repositories.ContactRepository;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{
    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    /**
     * Создает новый контакт в системе. Проводит валидацию номера телефона, чтобы он начинался с '+' и содержал только цифры (не более 11).
     *
     * @param id Идентификатор контакта.
     * @param firstname Имя контакта.
     * @param surname Фамилия контакта.
     * @param phone Номер телефона контакта.
     * @throws IllegalArgumentException Если номер телефона не соответствует формату.
     */
    @Override
    public void createContact(Long id, String firstname, String surname, String phone) {
        if (!validatePhoneNumber(phone)) {
            throw new IllegalArgumentException("Номер телефона должен начинаться с '+' и содержать только цифры!");
        }
        Contact contact = new Contact();
        contact.setId(id);
        contact.setFirstname(firstname);
        contact.setSurname(surname);
        contact.setPhoneNumber(phone);
        contactRepository.create(contact);
    }

    /**
     * Возвращает контакт по его уникальному идентификатору.
     *
     * @param id Идентификатор контакта.
     * @return Контакт с указанным идентификатором или null, если не найден.
     */
    @Override
    public Contact findById(Long id) {
        return contactRepository.read(id);
    }

    /**
     * Возвращает контакт по его номеру телефона.
     *
     * @param number Номер телефона контакта.
     * @return Контакт с указанным номером телефона или null, если не найден.
     */
    @Override
    public Contact findByNumber(String number) {
        return contactRepository.read(number);
    }

    /**
     * Удаляет контакт из системы по его уникальному идентификатору.
     *
     * @param id Идентификатор контакта для удаления.
     */
    @Override
    public void deleteById(Long id) {
        contactRepository.delete(id);
    }

    /**
     * Обновляет номер телефона существующего контакта в системе.
     *
     * @param id Идентификатор контакта.
     * @param newNumber Новый номер телефона.
     * @throws IllegalArgumentException Если контакт не найден.
     */
    @Override
    public void updatePhoneNumber(Long id, String newNumber) {
        Contact existingContact = contactRepository.read(id);
        if (existingContact == null) {
            throw new IllegalArgumentException("Контакт не найден!");
        }

        // Создание копии с новым номером
        Contact updatedContact = new Contact();
        updatedContact.setId(existingContact.getId());
        updatedContact.setFirstname(existingContact.getFirstname());
        updatedContact.setSurname(existingContact.getSurname());
        updatedContact.setPhoneNumber(newNumber);

        contactRepository.update(updatedContact);
    }

    /**
     * Возвращает список всех контактов в бд.
     *
     * @return Список контактов.
     */
    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.readAll();
    }


    // Блок валидации на уровне сервисов (имя и фамилию не стал проверять, ибо во всех системах по управлению контактами туда можно хоть что вносить)
    private boolean validatePhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        if (!phone.startsWith("+")) {
            return false;
        }

        String digits = phone.substring(1);

        if (!digits.matches("\\d+")) {
            return false;
        }
        if (digits.length() > 11) {
            return false;
        }

        return true;
    }
}
