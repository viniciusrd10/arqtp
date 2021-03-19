package br.com.original.rest.presenter;

import br.com.original.rest.model.Person;

public class PersonPresenter {

    private Long id;

    private String firstName;
    private String lastName;
    private Integer age;


    public PersonPresenter(Person entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.firstName = entity.getFirstName();
            this.lastName = entity.getLastName();
            this.age = entity.getAge();
        }
    }

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public Integer getAge() {
        return age;
    }

}
