package br.com.original.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${kafka.consumer.group-id}")
    private String groupId;

    @Value("${kafka.producer.security.password}")
    private String senhaKafka;

    @Value("${kafka.producer.security.protocol}")
    private String protocol;

    @Value("${kafka.producer.security.mechanism}")
    private String mechanism;

    @Value("${kafka.producer.security.user}")
    private String securityUser;

    @Value("${kafka.consumer.value-deserializer}")
    private String deserializer;

    public String getBootstrapAddress() {
        return bootstrapAddress;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getSenhaKafka() {
        return senhaKafka;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getMechanism() {
        return mechanism;
    }

    public String getSecurityUser() {
        return securityUser;
    }

    public String getDeserializer() {
        return deserializer;
    }

}
