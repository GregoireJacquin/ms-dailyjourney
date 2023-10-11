package com.example.msdailyjourney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MsDailyjourneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDailyjourneyApplication.class, args);
    }

}
