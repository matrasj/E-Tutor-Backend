package com.example.etutorbackend.model.payload.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPayload {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private int advertisementsForTutorQuantity;
    private int advertisementsForStudentQuantity;
    private String profileImagePath;


}
