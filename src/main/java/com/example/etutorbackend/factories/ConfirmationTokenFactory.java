package com.example.etutorbackend.factories;

import com.example.etutorbackend.model.entity.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConfirmationTokenFactory {
    private static final int MINUTES_TO_EXPIRE = 15;

    public static ConfirmationToken createConfirmationToken() {
        return ConfirmationToken.builder()
                .token(generateRandomToken())
                .minutesDuration(MINUTES_TO_EXPIRE)
                .createdAt(LocalDateTime.now())
                .expireAt(LocalDateTime.now().plusMinutes(MINUTES_TO_EXPIRE))
                .build();
    }

    private static String generateRandomToken() {
        return UUID.randomUUID().toString();
    }
}
