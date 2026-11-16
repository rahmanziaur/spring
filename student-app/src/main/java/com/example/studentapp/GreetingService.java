package com.example.studentapp;

import org.springframework.stereotype.Service;

// @Service marks this class as a bean that holds business logic.
// Spring creates ONE object of it at start-up and keeps it in the container.
@Service
public class GreetingService {

    public String greet(String name) {
        return "Hello, " + name + "! Welcome to Spring Boot.";
    }
}
