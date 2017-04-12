package com.airports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static final String REST_SERVICE_PREFIX = "/rest/";

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}