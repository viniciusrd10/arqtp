package br.com.original.rest.model;

import br.com.original.rest.entity.PersonEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class Person implements Serializable {


	private static final long serialVersionUID = 1492453980104260017L;

	@ApiModelProperty(value = "Código da pessoa")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ApiModelProperty(value = "Primeiro nome da pessoa")
    private String firstName;

	@ApiModelProperty(value = "Sobrenome da pessoa")
    private String lastName;

	@ApiModelProperty(value = "Idade da pessoa")
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

	public PersonEntity toEntity(){
		PersonEntity personEntity = new PersonEntity();
		personEntity.setFirstName(this.firstName);
		personEntity.setLastName(this.lastName);
		personEntity.setAge(this.age);
		personEntity.setId(this.id);
		return personEntity;
	}
}
