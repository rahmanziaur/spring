package com.example.studentapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// @RestController tells Spring: this class handles HTTP requests,
// and whatever the methods return is sent back as the response body.
@RestController
public class HelloController {

    // The dependency. "final" means it is set once and never changes.
    private final GreetingService greetingService;

    // CONSTRUCTOR INJECTION:
    // Spring sees that this constructor needs a GreetingService,
    // finds the bean in its container, and passes it in.
    public HelloController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // GET /hello
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World from Spring Boot!";
    }

    // GET /hello/Rahim
    // {name} is a variable part of the URL.
    // @PathVariable copies its value into the method parameter.
    @GetMapping("/hello/{name}")
    public String sayHelloTo(@PathVariable String name) {
        return greetingService.greet(name);
    }
}
