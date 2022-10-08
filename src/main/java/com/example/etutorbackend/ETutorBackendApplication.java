package com.example.etutorbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ETutorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ETutorBackendApplication.class, args);
    }

}
