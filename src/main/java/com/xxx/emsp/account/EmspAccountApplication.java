package com.xxx.emsp.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
public class EmspAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmspAccountApplication.class, args);
    }
}