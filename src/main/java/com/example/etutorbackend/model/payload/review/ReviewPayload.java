package com.example.etutorbackend.model.payload.review;

import com.example.etutorbackend.model.payload.user.UserPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewPayload {
    private Long id;
    private String content;
    private int starsNumber;
    private Date createdAt;
    private UserPayload user;

}
