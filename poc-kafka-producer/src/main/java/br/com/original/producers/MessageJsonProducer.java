package br.com.original.producers;

import br.com.original.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class MessageJsonProducer {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    public ListenableFuture<SendResult<String, Message>> sendMessage(String topic, Message message) {

        ListenableFuture<SendResult<String, Message>> future = kafkaTemplate.send(topic, message);
        return future;
    }
}