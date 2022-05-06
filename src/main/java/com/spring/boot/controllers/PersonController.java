package com.spring.boot.controllers;

import com.spring.boot.entities.Person;
import com.spring.boot.exceptions.PersonsException;
import com.spring.boot.services.PersonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons/")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Person>  getAllPerson(){
        return new ArrayList<>(personService.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person show(@PathVariable Long id) {
        Optional<Person> personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
            return personOpt.get();
        } else {
            throw new PersonsException(HttpStatus.NOT_FOUND, "Unable to find entry with id " + id );
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Validated(Person.BasicValidation.class) @RequestBody Person person, BindingResult result, @Value("#{request.requestURL}")
            StringBuffer originalUrl, HttpServletResponse response) {
        if (result.hasErrors()) {
            String errString = createErrorString(result);
            throw new PersonsException(HttpStatus.BAD_REQUEST, "Cannot save entry because: " + errString);
        }
        try {
            Person newPerson = personService.save(person);
            response.setHeader("Location", getLocationForUser(originalUrl, newPerson.getId()));
        } catch (Exception e) {
            throw new PersonsException(HttpStatus.UNPROCESSABLE_ENTITY, e);
        }
    }

    private String createErrorString(BindingResult result) {
        StringBuilder sb =  new StringBuilder();
        result.getAllErrors().forEach(error -> {
            if(error instanceof FieldError) {
                FieldError err= (FieldError) error;
                sb.append("Field '").append(err.getField()).append("' value error: ").append(err.getDefaultMessage()).append("\n");
            }
        });
        return sb.toString();
    }

    static String getLocationForUser(StringBuffer url, Object childIdentifier) {
        UriTemplate template = new UriTemplate(url.toString() + "/{id}");
        return template.expand(childIdentifier).toASCIIString();
    }
}
