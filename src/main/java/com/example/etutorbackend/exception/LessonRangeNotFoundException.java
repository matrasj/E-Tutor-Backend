package com.example.etutorbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class LessonRangeNotFoundException extends RuntimeException {
    public LessonRangeNotFoundException(String message) {
        super(message);
    }
}
