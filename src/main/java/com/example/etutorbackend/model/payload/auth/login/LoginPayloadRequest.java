package com.example.etutorbackend.model.payload.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPayloadRequest {
    private String username;
    private String password;
}
