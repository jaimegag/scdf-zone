package io.pivotal.api.samples.speciesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableCircuitBreaker
public class SpeciesDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpeciesDemoApplication.class, args);
    }
}