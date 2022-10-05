package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.auth.LoginPayloadRequest;
import com.example.etutorbackend.model.payload.auth.LoginPayloadResponse;
import com.example.etutorbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginPayloadResponse> login(@RequestBody LoginPayloadRequest loginPayloadRequest) {
        return ResponseEntity.status(OK)
                .body(authService.loginUser(loginPayloadRequest));
    }
}
