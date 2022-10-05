package com.example.etutorbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    private final static String GET = "GET";
    private final static String POST = "POST";
    private final static String PUT = "PUT";
    private final static String DELETE = "DELETE";
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowCredentials(true)
                        .allowedMethods(GET, POST, PUT, DELETE)
                        .allowedOriginPatterns("*")
                        .allowedHeaders("*");
            }
        };
    }
}
