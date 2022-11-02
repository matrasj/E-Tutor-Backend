package com.example.etutorbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class ETutorBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(ETutorBackendApplication.class, args);
    }
}
