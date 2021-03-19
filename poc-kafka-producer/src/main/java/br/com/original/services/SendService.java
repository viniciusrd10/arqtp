package br.com.original.services;

import br.com.original.models.Message;
import br.com.original.parameters.ScheduledReturnedParamerter;
import br.com.original.producers.MessageJsonProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SendService {

    @Autowired
    private MessageJsonProducer messageJsonProducer;

    @Value("${spring.kafka.properties.topic}")
    private String topic;

    public ResponseEntity<Void> execute(ScheduledReturnedParamerter parameter) {

        Message message = new Message(parameter);
        this.messageJsonProducer.sendMessage(topic, message);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
