package com.spring.boot.controllers;

import com.spring.boot.entities.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecuredRestApplicationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static TestRestTemplate testRestTemplate = null;

    @BeforeAll
    static void init() {
        testRestTemplate = new TestRestTemplate("jane", "doe");
    }

    @BeforeEach
    void setUp(){
        baseUrl = baseUrl.concat(":").concat(port +"").concat("/persons");
    }

    @Test
    void shouldReturnAListOfPersons(){
        ResponseEntity<Person[]> response = testRestTemplate.getForEntity(baseUrl, Person[].class);
        Person[] persons = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.
                        getStatusCode()),
                () -> assertNotNull(persons),
                () -> assertTrue(persons.length >= 4)
        );
    }

    @Test
    void shouldCreateAPersonUsingExchange() {
        Person person = buildPerson("gigi.pedala", "Gigi", "Pedala", "1dooh2" );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> postRequest = new HttpEntity<>(person, headers);
        ResponseEntity<Person> responseEntity = testRestTemplate.exchange(baseUrl,
                HttpMethod.POST, postRequest, Person.class);
        Person newPerson = responseEntity.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.
                        getStatusCode()),
                () -> assertNotNull(newPerson),
                () -> assertEquals(person.getUsername(), newPerson.
                        getUsername()),
                () -> assertNotNull(newPerson.getId())
        );
    }

    @Test
    void shouldUpdateAPerson() {
        Person person = buildPerson("sherlock.holmes", "Sherlock Tiberius", "Holmes", "complicated");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<Person> putRequest = new
                HttpEntity<>(person, headers);
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(baseUrl.concat("/1"), HttpMethod.PUT,
                putRequest, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.
                getStatusCode());
    }
    @Test
    void shouldNotUpdateAPerson403(){
        Person person = buildPerson("sherlock.holmes", "Sherlock Cornelius", "Holmes", "complicated");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<Person> putRequest = new
                HttpEntity<>(person, headers);
        ResponseEntity<Void> responseEntity = testRestTemplate
                .withBasicAuth("john", "doe").exchange(baseUrl.concat("/1"), HttpMethod.PUT,
                putRequest, Void.class);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.
                getStatusCode());
    }

    private Person buildPerson(final String username, final String firstName, final String lastName, final String password) {
        Person person = new Person();
        person.setUsername(username);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPassword(password);
        person.setHiringDate(LocalDate.now());
        return person;
    }
}
