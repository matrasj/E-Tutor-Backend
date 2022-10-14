package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.Review;
import com.example.etutorbackend.model.payload.review.ReviewPayload;

public class ReviewPayloadMapper {
    public static ReviewPayload mapToReviewPayload(Review review) {
        return ReviewPayload.builder()
                .id(review.getId())
                .content(review.getContent())
                .starsNumber(review.getRatingValue().getRatingValue())
                .createdAt(review.getCreatedAt())
                .user(UserPayloadMapper.mapToUserPayload(review.getUser()))
                .build();
    }
}
