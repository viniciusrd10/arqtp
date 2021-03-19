package br.com.original.rest.repository;

import br.com.original.fwcl.components.logger.OriginalLogLogger;
import br.com.original.fwcl.utils.AnnotationUtil;
import br.com.original.rest.entity.PersonEntity;
import br.com.original.rest.repository.PersonRepository;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * @author Henrique Romão
 */
@RunWith(PowerMockRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@PrepareForTest(PersonRepository.class)
public class PersonRepositoryTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private OriginalLogLogger originalLogLogger;

    @InjectMocks
    private PersonRepository personRepository;

    @Test
    public void shouldHaveAnnotations() {
        assertTrue(AnnotationUtil.hasAnnotation(PersonRepository.class, Repository.class));
    }

    @Test
    public void findAllShouldReceiveNoParameters() throws Exception {
        PersonRepository.class.getMethod("findAll", null);
    }

    @Test
    public void findByIdShouldReceiveParameters() throws Exception {
        PersonRepository.class.getMethod("findById", Long.class);
    }

    @Test
    public void saveShouldReceiveParameters() throws Exception {
        PersonRepository.class.getMethod("save", PersonEntity.class);
    }

    @Test
    public void deleteByIdShouldReceiveParameters() throws Exception {
        PersonRepository.class.getMethod("deleteById", Long.class);
    }

    /*
     * findAll()
     * */
    @Test
    public void findAllShouldReturnList() throws Exception {
        List<PersonEntity> personEntityList = new ArrayList<>();
        personEntityList.add(createTestPerson());
        personEntityList.add(createTestPerson());
        personEntityList.add(createTestPerson());

        StringBuilder sql = new StringBuilder()
                .append(" SELECT ")
                .append(" id, ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" FROM  ")
                .append(" PERSON ");

        when(jdbcTemplate.query(Mockito.eq(sql.toString()), any(BeanPropertyRowMapper.class)))
                .thenReturn(personEntityList);

        List<PersonEntity> personEntityListReturned = personRepository.findAll();

        assertThat(personEntityListReturned, not(IsEmptyCollection.empty()));
        assertThat(personEntityListReturned.size(), is(3));
        assertEquals(personEntityListReturned, personEntityList);
    }

    @Test
    public void findAllShouldReturnEmptyListWithIndexOutOfBoundsException() {

        StringBuilder sql = new StringBuilder()
                .append(" SELECT ")
                .append(" id, ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" FROM  ")
                .append(" PERSON ");

        when(jdbcTemplate.query(Mockito.eq(sql.toString()), any(BeanPropertyRowMapper.class)))
                .thenThrow(IndexOutOfBoundsException.class);

        List<PersonEntity> personEntityListReturned = personRepository.findAll();

        assertThat(personEntityListReturned, IsEmptyCollection.empty());
        assertThat(personEntityListReturned.size(), is(0));
    }

    @Test
    public void findAllShouldReturnEmptyListWithException() {
        StringBuilder sql = new StringBuilder()
                .append(" SELECT ")
                .append(" id, ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" FROM  ")
                .append(" PERSON ");

        when(jdbcTemplate.query(Mockito.eq(sql.toString()), any(BeanPropertyRowMapper.class)))
                .thenThrow(NullPointerException.class);

        List<PersonEntity> personEntityListReturned = personRepository.findAll();

        assertThat(personEntityListReturned, IsEmptyCollection.empty());
        assertThat(personEntityListReturned.size(), is(0));
    }


    /*
     * findById()
     * */
    @Test
    public void findByIdShouldReturnEntity() {
        PersonEntity expectedEntity = createTestPerson();

        StringBuilder sql = new StringBuilder()
                .append(" SELECT ")
                .append(" id, ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" FROM  ")
                .append(" PERSON ")
                .append(" WHERE ")
                .append(" id = :id ");

        when(jdbcTemplate.queryForObject(Mockito.eq(sql.toString()),
                Mockito.any(MapSqlParameterSource.class),
                any(BeanPropertyRowMapper.class)))
                .thenReturn(expectedEntity);

        PersonEntity personEntityReturned = personRepository.findById(1l);

        assertEquals(personEntityReturned, expectedEntity);
    }

    @Test
    public void findByIdShouldReturnNullWithIndexOutOfBoundsException() {

        StringBuilder sql = new StringBuilder()
                .append(" SELECT ")
                .append(" id, ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" FROM  ")
                .append(" PERSON ")
                .append(" WHERE ")
                .append(" id = :id ");

        when(jdbcTemplate.queryForObject(Mockito.eq(sql.toString()),
                Mockito.any(MapSqlParameterSource.class),
                any(BeanPropertyRowMapper.class)))
                .thenThrow(IndexOutOfBoundsException.class);

        PersonEntity personEntityReturned = personRepository.findById(1l);

        assertNull(personEntityReturned);
    }

    @Test
    public void findByIdShouldReturnNullWithException() {
        StringBuilder sql = new StringBuilder()
                .append(" SELECT ")
                .append(" id, ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" FROM  ")
                .append(" PERSON ")
                .append(" WHERE ")
                .append(" id = :id ");

        when(jdbcTemplate.queryForObject(Mockito.eq(sql.toString()),
                Mockito.any(MapSqlParameterSource.class),
                any(BeanPropertyRowMapper.class)))
                .thenThrow(NullPointerException.class);

        PersonEntity personEntityReturned = personRepository.findById(anyLong());

        assertNull(personEntityReturned);
    }

    /*
     * save()
     * */
    @Test
    public void saveShouldSaveAndReturnSaved() throws Exception {
        PersonEntity savedEntity = createTestPerson();
        PersonEntity expectedEntity = createTestPerson();

        StringBuilder sql = new StringBuilder()
                .append(" INSERT INTO PERSON ")
                .append(" ( ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" ) ")
                .append(" VALUES ( ")
                .append(" :first_name, ")
                .append(" :last_name, ")
                .append(" :age ")
                .append(" ) ");

        Number key = 1;
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("key1", key);
        List<Map<String, Object>> keyList = new ArrayList<>();
        keyList.add(keyMap);

        GeneratedKeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        whenNew(GeneratedKeyHolder.class).withNoArguments().thenReturn(keyHolder);

        when(keyHolder.getKey()).thenReturn(1);

        when(jdbcTemplate.update(Mockito.eq(sql.toString()),
                Mockito.any(MapSqlParameterSource.class),
                any(KeyHolder.class)))
                .thenReturn(1);

        when(jdbcTemplate.queryForObject(anyString(),
                Mockito.any(MapSqlParameterSource.class),
                any(BeanPropertyRowMapper.class)))
                .thenReturn(expectedEntity);

        PersonEntity personEntityReturned = personRepository.save(savedEntity);

        assertEquals(personEntityReturned, expectedEntity);
    }

    @Test
    public void saveShouldReturn0AndReturnNull() throws Exception {
        PersonEntity savedEntity = createTestPerson();

        StringBuilder sql = new StringBuilder()
                .append(" INSERT INTO PERSON ")
                .append(" ( ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" ) ")
                .append(" VALUES ( ")
                .append(" :first_name, ")
                .append(" :last_name, ")
                .append(" :age ")
                .append(" ) ");

        Number key = 1;
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("key1", key);
        List<Map<String, Object>> keyList = new ArrayList<>();
        keyList.add(keyMap);

        GeneratedKeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        whenNew(GeneratedKeyHolder.class).withNoArguments().thenReturn(keyHolder);

        when(keyHolder.getKey()).thenReturn(1);

        when(jdbcTemplate.update(Mockito.eq(sql.toString()),
                Mockito.any(MapSqlParameterSource.class),
                any(KeyHolder.class)))
                .thenReturn(0);

        PersonEntity personEntityReturned = personRepository.save(savedEntity);

        assertNull(personEntityReturned);
    }

    @Test
    public void saveShouldHaveExceptionAndReturnNull() throws Exception {
        PersonEntity savedEntity = createTestPerson();

        StringBuilder sql = new StringBuilder()
                .append(" INSERT INTO PERSON ")
                .append(" ( ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" ) ")
                .append(" VALUES ( ")
                .append(" :first_name, ")
                .append(" :last_name, ")
                .append(" :age ")
                .append(" ) ");

        Number key = 1;
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("key1", key);
        List<Map<String, Object>> keyList = new ArrayList<>();
        keyList.add(keyMap);

        GeneratedKeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        whenNew(GeneratedKeyHolder.class).withNoArguments().thenReturn(keyHolder);

        when(keyHolder.getKey()).thenReturn(1);

        when(jdbcTemplate.update(Mockito.eq(sql.toString()),
                Mockito.any(MapSqlParameterSource.class),
                any(KeyHolder.class)))
                .thenThrow(NullPointerException.class);

        PersonEntity personEntityReturned = personRepository.save(savedEntity);

        assertNull(personEntityReturned);
    }

    /*
     * deleteById()
     * */
    @Test
    public void deleteByIdShouldDeleteAndReturnTrue() throws Exception {

        StringBuilder sql = new StringBuilder()
                .append(" DELETE FROM PERSON ")
                .append(" WHERE id = :id ");

        when(jdbcTemplate.update(Mockito.eq(sql.toString()),
                Mockito.any(MapSqlParameterSource.class)))
                .thenReturn(1);

        boolean result = personRepository.deleteById(1l);

        assertTrue(result);
    }

    @Test
    public void deleteByIdShouldNotDeleteAndReturnFalse() throws Exception {

        StringBuilder sql = new StringBuilder()
                .append(" DELETE FROM PERSON ")
                .append(" WHERE id = :id ");

        when(jdbcTemplate.update(Mockito.eq(sql.toString()),
                Mockito.any(MapSqlParameterSource.class)))
                .thenReturn(0);

        boolean result = personRepository.deleteById(1l);

        assertFalse(result);
    }

    @Test
    public void deleteByIdShouldHaveExceptionAndReturnFalse() throws Exception {

        StringBuilder sql = new StringBuilder()
                .append(" DELETE FROM PERSON ")
                .append(" WHERE id = :id ");

        when(jdbcTemplate.update(Mockito.eq(sql.toString()),
                Mockito.any(MapSqlParameterSource.class)))
                .thenThrow(NullPointerException.class);

        boolean result = personRepository.deleteById(1l);

        assertFalse(result);
    }

    private PersonEntity createTestPerson() {
        PersonEntity expectedPerson = new PersonEntity();
        expectedPerson.setId(new Random().nextLong());
        expectedPerson.setAge(10);
        expectedPerson.setFirstName("TESTE");
        expectedPerson.setLastName("1234");
        return expectedPerson;
    }

}
