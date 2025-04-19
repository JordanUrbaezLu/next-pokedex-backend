package com.authapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * <p>A simple health‑check or greeting endpoint under `/api`.
 *
 * <p>• GET /api/hello – Returns a plain-text greeting (“Hello from the backend!”). – Useful for
 * verifying that the server is up and responding.
 */
@RestController
@RequestMapping("/api")
public class HelloController {

  @GetMapping("/hello")
  public String sayHello() {
    return "Hello from the backend!";
  }
}
