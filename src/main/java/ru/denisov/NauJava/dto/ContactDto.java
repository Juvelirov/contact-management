package ru.denisov.NauJava.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactDto {

    @NotBlank(message = "first name is required")
    @Size(max = 100, message = "should not be more than 100 characters")
    private String firstname;

    @NotBlank(message = "surname is required")
    @Size(max = 100, message = "should not be more than 100 characters")
    private String surname;

    @Size(max = 100, message = "should not be more than 100 characters")
    private String position;

    @Size(max = 20, message = "should not be more than 20 characters")
    private String phoneNumber;

    @Email(message = "email should be valid")
    @Size(max = 255, message = "email must be at most 255 characters")
    private String email;

    // Для указания id организации, которой пренадлежит контакт
    private Integer organizationId;
}
