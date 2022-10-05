package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.auth.login.UserAuthPayloadResponse;

public class UserAuthPayloadResponseMapper {
    public static UserAuthPayloadResponse mapToUserAuthPayloadResponse(User user) {
        return UserAuthPayloadResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
