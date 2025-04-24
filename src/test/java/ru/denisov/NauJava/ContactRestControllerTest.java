package ru.denisov.NauJava;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ContactRestControllerTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1/contacts";
    }

    @Test
    void testCreateContact() {
        String jsonBody = """
            {
                "firstname": "Александр",
                "surname": "Петров",
                "position": "Разработчик",
                "phoneNumber": "+79021889755",
                "email": "lexapetrov123@mail.ru"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("firstname", equalTo("Александр"))
                .body("surname", equalTo("Петров"));
    }

    @Test
    void testGetAllContacts() {
        when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", notNullValue())
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    void testGetContactById() {
        given()
                .pathParam("id", 4)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(4));
    }

    @Test
    void testGetNotExistContactById() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(containsString("not found"));
    }

    @Test
    void testUpdateContact() {
        int existingId = 5;

        String updateData = """
        {
            "firstname": "Вася",
            "surname": "Петрович",
            "position": "Охранник",
            "phoneNumber": "+79021889754",
            "email": "vasyagitelman@mail.ru"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", existingId)
                .body(updateData)
                .when()
                .put("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("position", equalTo("Охранник"));
    }

    @Test
    void testDeleteContact() {
        int existingId = 4;

        given()
                .pathParam("id", existingId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
