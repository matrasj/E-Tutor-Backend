package com.example.etutorbackend.model.payload.auth.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegisterPayloadRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

}
