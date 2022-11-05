package com.example.etutorbackend.model.payload.auth.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegisterPayloadRequest {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Size(min = 4, max = 25, message = "Username must be longer that 4 characters and less than 25")
    @NotBlank(message = "Username is required")
    private String username;
    @Size(min = 4, max = 25, message = "Password must be longer that 4 characters and less than 25")
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = "Email must be valid")
    private String email;

}
