package br.com.original.producers;

import br.com.original.configs.ApplicationProperties;
import br.com.original.cryptography.password.Decryptor;
import br.com.original.models.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerJsonConfig {

    @Autowired
    ApplicationProperties propss;

    private static final String SECURITYPROTOCOL = "security.protocol";
    private static final String SASLMECHANISM = "sasl.mechanism";
    private static final String JASCONFIG = "sasl.jaas.config";
    private static final String SECURITYUSERPASS = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";

    public String getPassKafka() {
        return new Decryptor().decrypt(propss.getSenhaKafka(), System.getenv("password"), System.getenv("algorithm"));
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, propss.getBootstrapAddress());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, propss.getGroupId());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        if (propss.getProtocol() != null) {
            String jaasTemplate = SECURITYUSERPASS;
            String jaasCfg = String.format(jaasTemplate, propss.getSecurityUser(), getPassKafka());
            props.put(SECURITYPROTOCOL, propss.getProtocol());
            props.put(SASLMECHANISM, propss.getMechanism());
            props.put(JASCONFIG, jaasCfg);
        }

        return props;
    }

    @Bean
    public ProducerFactory<String, Message> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplateJson() {
        return new KafkaTemplate<>(producerFactory());
    }


}