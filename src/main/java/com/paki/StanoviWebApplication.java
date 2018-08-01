package com.paki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StanoviWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(StanoviWebApplication.class, args).registerShutdownHook();
    }
}
