package com.example.etutorbackend.model.payload.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewPayloadRequest {
    @NotBlank(message = "Advertisement id is required")
    private Long advertisementId;
    @NotBlank(message = "Author id is required")
    private Long authorId;
    @NotBlank(message = "Star's number is required")
    private Long starsNumber;
    @NotBlank(message = "Content is required")
    public String content;
}
