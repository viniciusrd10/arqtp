package br.com.original.rest.controller;

import br.com.original.fwcl.annotations.OriginalLogger;
import br.com.original.fwcl.components.logger.OriginalLogLogger;
import br.com.original.rest.controller.PersonController;
import br.com.original.rest.model.Person;
import br.com.original.rest.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Henrique Romão
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @Mock
    @OriginalLogger
    private OriginalLogLogger logger;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    /*
     * Listar Todos
     */
    @Test
    public void shouldFetchAllPeople() throws Exception {
        List<Person> peopleList = new ArrayList<>();
        peopleList.add(createTestPerson());
        peopleList.add(createTestPerson());
        peopleList.add(createTestPerson());

        when(personService.listAll()).thenReturn(peopleList);

        this.mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(peopleList.size())));
    }

    @Test
    public void shouldFetchNoPeople() throws Exception {
        when(personService.listAll()).thenReturn(null);

        this.mockMvc.perform(get("/person"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void listPersonShouldReturnBadRequest() throws Exception {
        when(personService.listAll()).thenThrow(new NullPointerException());

        this.mockMvc.perform(get("/person"))
                .andExpect(status().isBadRequest());
    }

    /*
     * Consultar uma pessoa pelo id
     */
    @Test
    public void getPersonByIdShouldReturnOkAndExpectedPerson() throws Exception {
        Person expectedPerson = createTestPerson();
        when(personService.findById(anyLong())).thenReturn(expectedPerson);

        this.mockMvc.perform(get("/person/{id}", anyLong()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedPerson.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(expectedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedPerson.getLastName())))
                .andExpect(jsonPath("$.age", is(expectedPerson.getAge())));
    }

    @Test
    public void getPersonByIdShouldReturnNoContent() throws Exception {
        when(personService.findById(anyLong())).thenReturn(null);

        this.mockMvc.perform(get("/person/{id}", anyLong()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getPersonByIShouldReturnBadRequest() throws Exception {
        when(personService.findById(anyLong())).thenThrow(new NullPointerException());

        this.mockMvc.perform(get("/person/{id}", anyLong()))
                .andExpect(status().isBadRequest());
    }

    /*
     * Cadastrar uma pessoa
     */
    @Test
    public void savePersonShouldReturnCreated() throws Exception {
        Person expectedPerson = createTestPerson();
        when(personService.save(any())).thenReturn(expectedPerson);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(expectedPerson)))
                .andExpect(status().isCreated());
    }

    @Test
    public void savePersonShouldReturnBadRequest() throws Exception {
        Person createPerson = createTestPerson();
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createPerson)))
                .andExpect(status().isBadRequest());
    }

    /*
     * Alterar uma pessoa pelo id
     */
    @Test
    public void updatePersonShouldReturnNoContent() throws Exception {
        Person expectedPerson = createTestPerson();
        when(personService.findById(expectedPerson.getId())).thenReturn(expectedPerson);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/person/{id}", expectedPerson.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(expectedPerson)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updatePersonShouldReturnNotFound() throws Exception {
        Person createPerson = createTestPerson();
        when(personService.findById(anyLong())).thenReturn(null);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/person/{id}", anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createPerson)))
                .andExpect(status().isNotFound());
    }

    /*
     * Deletar uma pessoa pelo id
     */
    @Test
    public void deletePersonShouldReturnNoContent() throws Exception {
        when(personService.delete(anyLong())).thenReturn(true);

        this.mockMvc.perform(delete("/person/{id}", anyLong()))
                .andExpect(status().isNoContent());
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
