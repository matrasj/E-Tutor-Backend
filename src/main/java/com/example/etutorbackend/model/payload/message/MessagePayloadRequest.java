package com.example.etutorbackend.model.payload.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagePayloadRequest {
    @NotBlank(message = "Subject is required")
    public String subject;
    @NotBlank(message = "Content is required")
    private String content;
    private Long senderId;
    private Long receiverId;
}

