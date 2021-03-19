package br.com.original.rest.service;

import br.com.original.fwcl.annotations.OriginalLogger;
import br.com.original.fwcl.components.logger.OriginalLogLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Henrique Romão
 */
@Service
public class ConsumerService {

    private static final String CONSUMER_ERROR_CATEGORY = "consumer";

    @Autowired
    @OriginalLogger
    private OriginalLogLogger logger;

    @KafkaListener(topics = "${spring.kafka.topic.consumer}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics, @Header(KafkaHeaders.OFFSET) List<Long> offsets,
                       Acknowledgment ack) {
        logger.info(String.format("Received Messasge: %s", message));
        ack.acknowledge();
    }

}
