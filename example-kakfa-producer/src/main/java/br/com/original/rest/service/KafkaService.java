package br.com.original.rest.service;

import br.com.original.fwcl.annotations.OriginalLogger;
import br.com.original.fwcl.components.logger.OriginalLogLogger;
import br.com.original.rest.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author Henrique Romão
 */
@Service
public class KafkaService {

    @Autowired
    @OriginalLogger
    private OriginalLogLogger logger;

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Value(value = "${original.arqr.kafka.topic}")
    private String topicName;

    public void sendMessage(Message message) {

        ListenableFuture<SendResult<String, Message>> future =
                kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Message> result) {
                logger.info("Sent message=[" + message.getMessage() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=["
                        + message.getMessage() + "] due to : " + ex.getMessage());
            }
        });
    }
}
