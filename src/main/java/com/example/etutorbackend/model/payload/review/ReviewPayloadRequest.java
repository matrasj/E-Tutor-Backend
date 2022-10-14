package com.example.etutorbackend.model.payload.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewPayloadRequest {
    private Long advertisementId;
    private Long authorId;
    private Long starsNumber;
    public String content;
}
