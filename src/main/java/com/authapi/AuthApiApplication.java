package com.authapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AuthApiApplication
 *
 * <p>Entry point for the Auth API Spring Boot application.
 *
 * <p>• Annotated with @SpringBootApplication to enable component scanning, auto‑configuration, and
 * Spring Boot’s features. • Contains the main() method which bootstraps the Spring context and
 * starts the embedded web server.
 */
@SpringBootApplication
public class AuthApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthApiApplication.class, args);
  }
}
