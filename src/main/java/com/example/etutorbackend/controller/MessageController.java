package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.message.MessagePayloadRequest;
import com.example.etutorbackend.model.payload.message.MessagePayloadResponse;
import com.example.etutorbackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/pagination/sent/findByUserId/{userId}")
    public ResponseEntity<Page<MessagePayloadResponse>> getMessagesSentForUser(@PathVariable Long userId,
                                                                               @RequestParam int pageSize,
                                                                               @RequestParam int pageNumber) {
        return ResponseEntity.status(OK)
                .body(messageService.findSentMessagesByUserIdWithPagination(userId, pageSize, pageNumber));
    }

    @GetMapping("/pagination/received/findByUserId/{userId}")
    public ResponseEntity<Page<MessagePayloadResponse>> getMessagesReceivedByUser(@PathVariable Long userId,
                                                                               @RequestParam int pageSize,
                                                                               @RequestParam int pageNumber) {
        return ResponseEntity.status(OK)
                .body(messageService.findReceivedMessagesByUserIdWithPagination(userId, pageSize, pageNumber));
    }


    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody MessagePayloadRequest messagePayloadRequest) {
        return ResponseEntity.status(CREATED)
                .body(messageService.createMessage(messagePayloadRequest));
    }
}
