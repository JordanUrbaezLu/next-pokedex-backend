package com.authapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class AuthApiApplication {
    public static void main(String[] args) {
        // load .env from project root
        Dotenv dotenv = Dotenv.load();

        // copy each into a System property so Spring can see it
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));

        SpringApplication.run(AuthApiApplication.class, args);
    }
}