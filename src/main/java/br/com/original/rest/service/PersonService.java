package br.com.original.rest.service;

import br.com.original.rest.entity.PersonEntity;
import br.com.original.rest.model.Person;
import br.com.original.rest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> listAll() {
        List<PersonEntity> list = personRepository.findAll();
        List<Person> listPerson = list.stream().map(PersonEntity::toModel).collect(Collectors.toList());

        return listPerson;
    }

    public Person findById(Long id) {
        PersonEntity t = personRepository.findById(id);
        return t.toModel();
    }

    public Person save(Person person) {
        return this.personRepository.save(person.toEntity()).toModel();
    }

    public boolean delete(Long id) {
        return this.personRepository.deleteById(id);
    }

}
