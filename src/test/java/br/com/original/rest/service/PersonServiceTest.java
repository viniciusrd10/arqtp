package br.com.original.rest.service;

import br.com.original.fwcl.utils.AnnotationUtil;
import br.com.original.rest.entity.PersonEntity;
import br.com.original.rest.model.Person;
import br.com.original.rest.repository.PersonRepository;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Henrique Romão
 */

@RunWith(PowerMockRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    public void shouldHaveAnnotations() {
        assertTrue(AnnotationUtil.hasAnnotation(PersonService.class, Service.class));
    }

    @Test
    public void listAllShouldReceiveNoParameters() throws Exception {
        PersonService.class.getMethod("listAll", null);
    }

    @Test
    public void findByIdShouldReceiveParameters() throws Exception {
        PersonService.class.getMethod("findById", Long.class);
    }

    @Test
    public void saveShouldReceiveParameters() throws Exception {
        PersonService.class.getMethod("save", Person.class);
    }

    @Test
    public void deleteShouldReceiveParameters() throws Exception {
        PersonService.class.getMethod("delete", Long.class);
    }

    @Test
    public void shouldListAll() {
        PersonEntity personEntity = mock(PersonEntity.class);

        List<PersonEntity> expectedEntityList = new ArrayList<>();
        expectedEntityList.add(personEntity);

        Person expectedPerson = createTestPerson();

        List<Person> expectedPersonList = new ArrayList<>();
        expectedPersonList.add(expectedPerson);

        when(personRepository.findAll()).thenReturn(expectedEntityList);
        when(personEntity.toModel()).thenReturn(expectedPerson);
        List<Person> returnedPersonList = this.personService.listAll();

        assertThat(returnedPersonList, not(IsEmptyCollection.empty()));
        assertThat(returnedPersonList.size(), is(1));
        assertEquals(returnedPersonList, expectedPersonList);
    }

    @Test
    public void shouldFindById() {
        PersonEntity personEntity = mock(PersonEntity.class);

        Person expectedPerson = createTestPerson();

        when(this.personRepository.findById(1l)).thenReturn(personEntity);
        when(personEntity.toModel()).thenReturn(expectedPerson);

        Person returnedPeron = this.personService.findById(1l);
        assertEquals(returnedPeron, expectedPerson);
        verify(personEntity).toModel();

    }

    @Test
    public void shouldSavePerson() {
        Person entryPerson = mock(Person.class);
        PersonEntity savedEntity = mock(PersonEntity.class);
        Person expectedPerson = createTestPerson();

        PersonEntity entryEntity = mock(PersonEntity.class);

        when(entryPerson.toEntity()).thenReturn(entryEntity);
        when(this.personRepository.save(entryEntity)).thenReturn(savedEntity);
        when(savedEntity.toModel()).thenReturn(expectedPerson);

        Person savedPerson = this.personService.save(entryPerson);
        assertEquals(savedPerson, expectedPerson);
    }

    @Test
    public void shouldDeletePerson() {
        when(this.personRepository.deleteById(1l)).thenReturn(true);
        assertTrue(this.personService.delete(1l));
    }

    private Person createTestPerson() {
        Person expectedPerson = new Person();
        expectedPerson.setId(1l);
        expectedPerson.setAge(10);
        expectedPerson.setFirstName("TESTE");
        expectedPerson.setLastName("1234");
        return expectedPerson;
    }

}
