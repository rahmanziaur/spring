package com.example.studentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication is three annotations in one:
//   @Configuration            - this class can define beans
//   @EnableAutoConfiguration  - let Spring Boot configure things automatically
//   @ComponentScan            - find our classes in this package and its sub-packages
@SpringBootApplication
public class StudentAppApplication {

    public static void main(String[] args) {
        // Starts Spring, creates all objects (beans), and starts the embedded Tomcat server
        SpringApplication.run(StudentAppApplication.class, args);
    }
}
