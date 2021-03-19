package br.com.original.arqr.mappers;

import br.com.original.arqr.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Henrique Romão
 */
public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setFirstName(rs.getString("FIRST_NAME"));
        person.setLastName(rs.getString("LAST_NAME"));

        return person;
    }
}
