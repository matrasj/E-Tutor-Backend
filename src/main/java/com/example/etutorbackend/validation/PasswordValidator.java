package com.example.etutorbackend.validation;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class PasswordValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return s.length() >= 4;
    }
}
