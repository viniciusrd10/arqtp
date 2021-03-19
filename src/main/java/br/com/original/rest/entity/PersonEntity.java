package br.com.original.rest.entity;

import br.com.original.rest.model.Person;

import java.io.Serializable;

public class PersonEntity implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person toModel() {
        Person person = new Person();

        person.setId(this.id);
        person.setFirstName(this.firstName);
        person.setLastName(this.lastName);
        person.setAge(this.age);

        return person;
    }


}
