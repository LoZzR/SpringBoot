package com.spring.boot.controllers;

import com.spring.boot.entities.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerTests {

    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate = null;
    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }
    @BeforeEach
    void setUp(){
        baseUrl = baseUrl.concat(":").concat(port +"").concat("/boot/persons");
    }

    @Test
    @Sql(statements = {"INSERT INTO PERSON(ID, " +
            "USERNAME, FIRST_NAME,LAST_NAME, PASSWORD, " +
            "HIRING_DATE, VERSION, CREATED_AT, MODIFIED_AT)" +
            "VALUES (1, 'irene.adler', 'Irene', 'Adler', " +
            "'id123ds', '1990-08-18', 1, '1990-07-18', '1998-01-18');"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAPersonWithCallback(){
        String url = baseUrl + "/{id}"; // http://localhost:XXXX/persons/1
        Person person = restTemplate.execute(url, HttpMethod.GET,
                new RequestCallback() {
                    @Override
                    public void doWithRequest(ClientHttpRequest request) {
                        HttpHeaders headers = request.getHeaders();
                        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
                        System.out.println("Request headers = " + headers);
                    }
                }, new HttpMessageConverterExtractor<>(Person.class,
                        restTemplate.getMessageConverters())
                , new HashMap<String, Long>() {{
                    put("id", 1L); // -> http://localhost:XXXX/persons/1
                }});
        assertAll(
                () -> assertNotNull(person),
                () -> assertEquals("irene.adler", person.getUsername())
        );
    }

    @Test
    void shouldCreateAPersonUsingExchange() {
        Person person = buildPerson("gigi.pedala", "Gigi", "Pedala", "1dooh2" );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> postRequest = new HttpEntity<>(person, headers);
        ResponseEntity<Person> responseEntity = restTemplate.exchange(baseUrl,
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
