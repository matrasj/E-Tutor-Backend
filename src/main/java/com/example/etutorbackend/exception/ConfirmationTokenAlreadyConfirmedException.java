package com.example.etutorbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class ConfirmationTokenAlreadyConfirmedException extends RuntimeException{
    public ConfirmationTokenAlreadyConfirmedException(String message) {
        super(message);
    }
}
