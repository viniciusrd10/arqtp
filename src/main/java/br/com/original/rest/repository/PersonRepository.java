package br.com.original.rest.repository;

import br.com.original.fwcl.annotations.OriginalLogger;
import br.com.original.fwcl.components.logger.OriginalLogLogger;
import br.com.original.rest.entity.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {
    @Autowired
    @OriginalLogger
    private OriginalLogLogger logger;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<PersonEntity> findAll() {
        StringBuilder sql = new StringBuilder()
                .append(" SELECT ")
                .append(" id, ")
                .append(" first_name, ")
                .append(" last_name, ")
                .append(" age ")
                .append(" FROM  ")
                .append(" PERSON ");
        try {
            List<PersonEntity> personEntities = (List<PersonEntity>) jdbcTemplate.query(sql.toString(),
                    new BeanPropertyRowMapper(PersonEntity.class));
            return personEntities;
        } catch (IndexOutOfBoundsException ex) {
            logger.info(String.format("No record found on %s for the requested filter", "PersonEntity"));
            return new ArrayList<>();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return new ArrayList<>();
    }

    public PersonEntity findById(Long id) {
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
        try {
            PersonEntity personEntity = (PersonEntity) jdbcTemplate.queryForObject(sql.toString(),
                    new MapSqlParameterSource("id", id),
                    new BeanPropertyRowMapper(PersonEntity.class));
            return personEntity;
        } catch (IndexOutOfBoundsException ex) {
            logger.info(String.format("No record found on %s for the requested filter", "PersonEntity"));
            return null;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public PersonEntity save(PersonEntity personEntity) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO PERSON ")
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

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int count = jdbcTemplate.update(sql.toString(),
                    new MapSqlParameterSource("first_name", personEntity.getFirstName())
                            .addValue("last_name", personEntity.getLastName())
                            .addValue("age", personEntity.getAge()),
                    keyHolder);
            if (count > 0) {
                return this.findById(keyHolder.getKey().longValue());
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public boolean deleteById(Long id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" DELETE FROM PERSON ")
                .append(" WHERE id = :id ");

        try {
            int count = jdbcTemplate.update(sql.toString(),
                    new MapSqlParameterSource("id", id));
            if (count > 0) {
                return true;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return false;
    }
}
