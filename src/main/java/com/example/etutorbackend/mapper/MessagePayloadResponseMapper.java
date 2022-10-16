package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.Message;
import com.example.etutorbackend.model.payload.message.MessagePayloadResponse;

public class MessagePayloadResponseMapper {
    public static MessagePayloadResponse mapToMessagePayloadResponse(Message message) {
        return MessagePayloadResponse.builder()
                .id(message.getId())
                .sender(UserPayloadMapper.mapToUserPayload(message.getSender()))
                .receiver(UserPayloadMapper.mapToUserPayload(message.getReceiver()))
                .subject(message.getSubject())
                .content(message.getContent())
                .seen(message.isSeen())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
