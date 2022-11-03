package com.example.etutorbackend.exception;

import org.springframework.web.bind.annotation.ResponseStatus;


public class JwtTokenException extends RuntimeException{
    public JwtTokenException(String message) {
        super(message);
    }
}
