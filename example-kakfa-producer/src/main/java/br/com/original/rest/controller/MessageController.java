package br.com.original.rest.controller;

import br.com.original.fwcl.annotations.OriginalLogger;
import br.com.original.fwcl.components.logger.OriginalLogLogger;
import br.com.original.rest.model.Message;
import br.com.original.rest.presenter.MessagePresenter;
import br.com.original.rest.service.KafkaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Random;

@RestController
@RequestMapping(path = "/message")
@Api
public class MessageController {

    @Autowired
    @OriginalLogger
    private OriginalLogLogger logger;

    @Autowired
    private KafkaService kafkaService;

    @ApiOperation(" Enviar uma mensagem ")
    @RequestMapping(method = RequestMethod.POST)
    @ApiImplicitParams(
            @ApiImplicitParam(name = "x-transaction-id", value = "Transaction ID", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "3bd28702-0fc1-4802-814a-9de747de3de2", type = "string")
    )
    public ResponseEntity savePerson(@RequestBody MessagePresenter message) throws Exception {
        try {
            this.kafkaService.sendMessage(new Message(new Random().nextLong(), message.getMessage()));
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("Erro ao enviar mensagem.");
            logger.error(e.getMessage());
            return new ResponseEntity<>(new HashMap<>().put("message", "Ocorreu na requisição"),
                    HttpStatus.BAD_REQUEST);
        }

    }

}
