package com.example.simplespringwebfluxapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class SimpleSpringWebfluxAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSpringWebfluxAppApplication.class, args);
    }

}
