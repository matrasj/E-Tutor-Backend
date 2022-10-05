package com.example.etutorbackend.service;

import com.example.etutorbackend.model.payload.auth.LoginPayloadRequest;
import com.example.etutorbackend.model.payload.auth.LoginPayloadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ApplicationUserDetailsService applicationUserDetailsService;
    public LoginPayloadResponse loginUser(LoginPayloadRequest request) {
        return applicationUserDetailsService.createJwtToken(request);
    }
}
