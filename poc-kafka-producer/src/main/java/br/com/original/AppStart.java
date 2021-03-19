package br.com.original;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "br.com.original")
@EnableCaching
public class AppStart {

	public static void main(String[] args) {
		SpringApplication.run(AppStart.class, args);
	}

}