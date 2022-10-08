package com.example.etutorbackend.model.payload.auth.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthPayloadResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
