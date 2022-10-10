package com.example.etutorbackend.model.payload.auth.login;

import com.example.etutorbackend.model.payload.user.UserPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginPayloadResponse {
    private String jwtToken;
    private UserPayload userPayload;
}
