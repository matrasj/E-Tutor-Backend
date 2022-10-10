package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.user.UserPayload;

public class UserPayloadMapper {
    public static UserPayload mapToUserPayload(User user) {
        return UserPayload.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .city(user.getCity())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
