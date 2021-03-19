package br.com.original.controllers;

import br.com.original.parameters.ScheduledReturnedParamerter;
import br.com.original.services.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
public class SendController {

    @Autowired
    private SendService sendService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Void> validateReceivedData(
            @RequestBody ScheduledReturnedParamerter body) {
        return sendService.execute(body);
    }
}
