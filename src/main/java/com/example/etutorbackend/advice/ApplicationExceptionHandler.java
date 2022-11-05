package com.example.etutorbackend.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> handleMethodArgumentExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errorsMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach((fieldError -> {
                    errorsMap.put("error", fieldError.getDefaultMessage());
                }));

        return errorsMap;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> handleCustomException(RuntimeException exception) {
        HashMap<String, String> exceptionWithMessage = new HashMap<>();
        exceptionWithMessage.put("error", exception.getMessage());

        return exceptionWithMessage;
    }
}
