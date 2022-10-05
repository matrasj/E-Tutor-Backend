package com.example.etutorbackend.model.payload.auth.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class RegisterPayloadResponse {
    private String confirmationToken;
    private LocalDateTime expiresAt;
    private int minutesDuration;
}
