package com.authapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GoodByeController {

    @GetMapping("/goodbye")
    public String sayHello() {
        return "Goodbye from the backend :(";
    }
}
