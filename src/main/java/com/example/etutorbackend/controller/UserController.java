package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.user.UserPayload;
import com.example.etutorbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<UserPayload> getUser(@PathVariable Long userId) {
        return ResponseEntity.status(OK)
                .body(userService.findUserById(userId));
    }

    @PostMapping("/{userId}/profile-image")
    public ResponseEntity<String> updateUserProfileImage(@PathVariable Long userId,
                                                         @RequestBody MultipartFile imageFile) throws IOException {
        return ResponseEntity.status(ACCEPTED)
                .body(userService.updateUserProfileImageByUserId(userId, imageFile));
    }

    @DeleteMapping("/{userId}/profile-image")
    public ResponseEntity<String> removeUserProfileImage(@PathVariable Long userId) {
        return ResponseEntity.status(ACCEPTED)
                .body(userService.removeProfileImageByUserId(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserPayload userPayload) {
        return ResponseEntity.status(ACCEPTED)
                .body(userService.updateUserById(userPayload, userId));
    }

//    @GetMapping("/{userId}/completion-percent")
//    public ResponseEntity<> getCompletionPercent(@PathVariable Long userId) {
//        return ResponseEntity.status(OK)
//                .body(userService.getCompletionPercentByUserI)
//    }



}
