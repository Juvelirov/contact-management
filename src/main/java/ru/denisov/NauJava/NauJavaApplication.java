package ru.denisov.NauJava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.denisov.NauJava.Entity.Contact;
import ru.denisov.NauJava.Services.ContactService;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class NauJavaApplication implements CommandLineRunner {

	private final ContactService contactService;

	@Autowired
	public NauJavaApplication(ContactService contactService) {
		this.contactService = contactService;
	}

	public static void main(String[] args) {
		SpringApplication.run(NauJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		showMenu();
		while (true) {
			System.out.print("\nВведите команду: ");
			String command = scanner.nextLine().trim();

			switch (command.toLowerCase()) {
				case "1" -> createContact(scanner);
				case "2" -> findById(scanner);
				case "3" -> findByNumber(scanner);
				case "4" -> updatePhoneNumber(scanner);
				case "5" -> deleteContact(scanner);
				case "6" -> showAllContacts();
				case "exit" -> {
					System.out.println("Выход...");
					return;
				}
				default -> System.out.println("Такой команды не существует!");
			}
		}
	}

	private void showMenu() {
		System.out.println("""
            --- УПРАВЛЕНИЕ КОНТАКТАМИ ---
            1. Добавить контакт
            2. Найти контакт по ID
            3. Найти контакт по номеру телефона
            4. Обновить номер телефона у контакта
            5. Удалить контакт
            6. Показать все доступные контакты
            Введите exit для выхода...""");
	}

	private void createContact(Scanner scanner) {
		try {
			System.out.print("Введите ID контакта: ");
			Long id = Long.parseLong(scanner.nextLine());

			System.out.print("Введите имя: ");
			String firstname = scanner.nextLine();

			System.out.print("Введите фамилию: ");
			String surname = scanner.nextLine();

			System.out.print("Введите номер телефона: ");
			String phone = scanner.nextLine();

			contactService.createContact(id, firstname, surname, phone);
			System.out.println("Успех! Контакт создан!");
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void findById(Scanner scanner) {
		System.out.print("Введите ID для поиска: ");
		Long id = Long.parseLong(scanner.nextLine());
		Contact contact = contactService.findById(id);
		printContact(contact);
	}

	private void findByNumber(Scanner scanner) {
		System.out.print("Введите номер для поиска: ");
		String number = scanner.nextLine();
		Contact contact = contactService.findByNumber(number);
		printContact(contact);
	}

	private void updatePhoneNumber(Scanner scanner) {
		try {
			System.out.print("Введите ID контакта: ");
			Long id = Long.parseLong(scanner.nextLine());

			System.out.print("Введите новый номер: ");
			String newNumber = scanner.nextLine();

			contactService.updatePhoneNumber(id, newNumber);
			System.out.println("Номер успешно обновлен!");
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}

	private void deleteContact(Scanner scanner) {
		System.out.print("Введите ID для удаления: ");
		Long id = Long.parseLong(scanner.nextLine());
		contactService.deleteById(id);
		System.out.println("Контакт удален!");
	}

	private void showAllContacts() {
		List<Contact> contacts = contactService.getAllContacts();
		if (contacts.isEmpty()) {
			System.out.println("Контакты отсутствуют!");
			return;
		}
		System.out.println("\n----- Список всех контактов -----");
		contacts.forEach(this::printContact);
		System.out.println("-----------------------");
	}

	private void printContact(Contact contact) {
		if (contact == null) {
			System.out.println("Контакт не найден!");
			return;
		}

		System.out.println("ID: " + contact.getId());
		System.out.println("Имя: " + contact.getFirstname());
		System.out.println("Фамилия: " + contact.getSurname());
		System.out.println("Телефон: " + contact.getPhoneNumber());
		System.out.println("----------------------");
	}
}
