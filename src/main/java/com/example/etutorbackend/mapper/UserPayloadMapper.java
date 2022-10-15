package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.AdvertisementType;
import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.user.UserPayload;

import java.util.stream.Collectors;

import static com.example.etutorbackend.model.entity.AdvertisementType.LOOKING_FOR_STUDENT;
import static com.example.etutorbackend.model.entity.AdvertisementType.LOOKING_FOR_TUTOR;

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
                .advertisementsForTutorQuantity(user.getAdvertisements()
                        .stream()
                        .filter((advertisement -> advertisement.getAdvertisementType().equals(LOOKING_FOR_TUTOR)))
                        .toList()
                        .size())
                .advertisementsForStudentQuantity(user.getAdvertisements()
                        .stream()
                        .filter((advertisement -> advertisement.getAdvertisementType().equals(LOOKING_FOR_STUDENT)))
                        .toList()
                        .size())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
