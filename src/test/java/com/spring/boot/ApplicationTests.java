package com.spring.boot;

import com.spring.boot.entities.Person;
import com.spring.boot.services.PersonService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ApplicationTests {

	private Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

	@Autowired
	private PersonService personService;

	@Autowired
	private DataSource ds;

	@Test
	void contextLoads() {
	}

	@Test
	void testFindAll() {
		Set<Person> persons = personService.findAll();
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>testFindAll : {}", persons);
		assertNotNull(persons);
		assertEquals(2, persons.size());
	}

	@Test
	@Transactional
	@Rollback//by default, just add for example
	public void testSavePerson(){
		Person person = new Person();
		person.setFirstName("Irene");person.setLastName("Adler");
		personService.save(person);
		assertEquals(3, personService.count());
	}

}
