package com.sofkau.bank.audit;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class AuditServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditServiceApplication.class, args);
	}

}
