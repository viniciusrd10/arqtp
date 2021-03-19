package br.com.original.rest.controller;

import br.com.original.fwcl.annotations.OriginalLogger;
import br.com.original.fwcl.components.logger.OriginalLogLogger;
import br.com.original.rest.model.Person;
import br.com.original.rest.presenter.PersonPresenter;
import br.com.original.rest.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/person")
@Api
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    @OriginalLogger
    private OriginalLogLogger logger;

    @ApiOperation("Listar todas as pessoas")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity listPerson() {
        logger.info("inciando metodo listagem");
        try {
            List<Person> people = this.personService.listAll();
            if (people != null && !people.isEmpty()) {
                return new ResponseEntity<>(people.stream().map(a -> new PersonPresenter(a)).collect(toList()),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            logger.info("Erro ao buscar lista de pessoas.");
            logger.error(e.getMessage());
            return new ResponseEntity<>(new HashMap<>().put("message", "Ocorreu na requisição"),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation("Consultar uma pessoa pelo id")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity personById(@PathVariable Long id) throws Exception {
        try {
            Person person = this.personService.findById(id);

            if (person != null) {
                return new ResponseEntity<>(new PersonPresenter(person), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            logger.info("Erro ao buscar pessoa registrados.");
            logger.error(e.getMessage());
            return new ResponseEntity<>(new HashMap<>().put("message", "Ocorreu na requisição"),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(" Cadastrar uma pessoa ")
    @RequestMapping(method = RequestMethod.POST)
    @ApiImplicitParams(
            @ApiImplicitParam(name = "x-transaction-id", value = "Transaction ID", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "3bd28702-0fc1-4802-814a-9de747de3de2", type = "string")
    )
    public ResponseEntity savePerson(@RequestBody Person person) throws Exception {
        try {
            Person personSaved = this.personService.save(person);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(personSaved.getId()).toUri();

            return ResponseEntity.created(location).build();

        } catch (Exception e) {
            logger.info("Erro ao alterar pessoa registrados.");
            logger.error(e.getMessage());
            return new ResponseEntity<>(new HashMap<>().put("message", "Ocorreu na requisição"),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(" Alterar uma pessoa pelo id")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiImplicitParams(
            @ApiImplicitParam(name = "x-transaction-id", value = "Transaction ID", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "3bd28702-0fc1-4802-814a-9de747de3de2", type = "string")
    )
    public ResponseEntity<Object> updatePerson(@RequestBody Person person, @PathVariable long id) {

        Person personUpdated = this.personService.findById(id);

        if (personUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        person.setId(id);

        this.personService.save(person);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(" Deletar uma pessoa pelo id")
    @DeleteMapping("/{id}")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "x-transaction-id", value = "Transaction ID", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "3bd28702-0fc1-4802-814a-9de747de3de2", type = "string")
    )
    public ResponseEntity<Object> deletePerson(@PathVariable long id) {
        this.personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
