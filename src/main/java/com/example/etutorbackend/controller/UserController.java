package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.auth.UserAuthPayloadResponse;
import com.example.etutorbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<UserAuthPayloadResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.status(OK)
                .body(userService.findUserById(userId));
    }

}
