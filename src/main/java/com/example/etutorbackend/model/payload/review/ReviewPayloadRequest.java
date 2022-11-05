package com.example.etutorbackend.model.payload.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewPayloadRequest {
    private Long advertisementId;
    private Long authorId;
    private Long starsNumber;
    @NotBlank(message = "Content is required")
    public String content;
}
