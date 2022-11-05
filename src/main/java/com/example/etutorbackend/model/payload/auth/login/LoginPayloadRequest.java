package com.example.etutorbackend.model.payload.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPayloadRequest {
    @NotBlank(message = "Username can not be null or empty")
    private String username;
    @NotBlank(message = "Password can not be null or empty")
    private String password;
}
