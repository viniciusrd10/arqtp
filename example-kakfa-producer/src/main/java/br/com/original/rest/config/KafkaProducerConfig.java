package br.com.original.rest.config;

import br.com.original.fwcl.utils.SecurityUtil;
import br.com.original.rest.model.Message;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Henrique Romão
 */
@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${original.kafka.security.user}")
    private String user;

    @Value(value = "${spring.kafka.properties.security.protocol}")
    private String protocol;

    @Value(value = "${spring.kafka.properties.sasl.mechanism}")
    private String mechanism;

    @Bean
    public ProducerFactory<String, Message> producerFactory(SecurityUtil securityUtil) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        //Security
        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";

        String passwordDecrypted = securityUtil.getCryptedValue("original.kafka.security.password");
        String jaasCfg = String.format(jaasTemplate, user, passwordDecrypted);
        configProps.put("security.protocol", protocol);
        configProps.put("sasl.mechanism", mechanism);
        configProps.put("sasl.jaas.config", jaasCfg);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate(SecurityUtil securityUtil) {
        return new KafkaTemplate<>(producerFactory(securityUtil));
    }
}
