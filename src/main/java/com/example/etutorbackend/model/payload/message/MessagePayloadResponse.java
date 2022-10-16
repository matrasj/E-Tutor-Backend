package com.example.etutorbackend.model.payload.message;

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
public class MessagePayloadResponse {
    private Long id;
    private UserPayload sender;
    private UserPayload receiver;
    private String subject;
    private String content;
    private boolean seen;
    private Date createdAt;
}
