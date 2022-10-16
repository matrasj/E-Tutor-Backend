package com.example.etutorbackend.model.payload.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagePayloadRequest {
    public String subject;
    private String content;
    private Long senderId;
    private Long receiverId;
}

