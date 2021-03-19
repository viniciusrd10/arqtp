package br.com.original.rest;

import br.com.original.fwcl.annotations.LogIgnore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"br.com.original"})
@EntityScan(basePackages = "br.com.original.rest.entity")
@EnableJpaRepositories(basePackages = "br.com.original.rest.repository")
//Exemplo ignorando campo do log com a anotacao LogIgnore
@LogIgnore(requestFields = {"firstName"}, responseFields = {"lastName"})
public class ExampleApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ExampleApplication.class, args);
    }

}


