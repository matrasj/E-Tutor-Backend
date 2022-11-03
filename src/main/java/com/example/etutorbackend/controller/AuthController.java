package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.auth.login.LoginPayloadRequest;
import com.example.etutorbackend.model.payload.auth.login.LoginPayloadResponse;
import com.example.etutorbackend.model.payload.auth.register.RegisterPayloadRequest;
import com.example.etutorbackend.model.payload.auth.register.RegisterPayloadResponse;
import com.example.etutorbackend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<RegisterPayloadResponse> register(@RequestBody RegisterPayloadRequest registerPayloadRequest) {
        return ResponseEntity.status(CREATED)
                .body(authService.registerUser(registerPayloadRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginPayloadResponse> login(@RequestBody LoginPayloadRequest loginPayloadRequest)  {
        return ResponseEntity.status(OK)
                .body(authService.loginUser(loginPayloadRequest));
    }

    @GetMapping("/confirmation")
    public ResponseEntity<String> confirmAccount(@RequestParam String token) {
        return ResponseEntity.status(OK)
                .body(authService.confirmAccountByToken(token));
    }
}
