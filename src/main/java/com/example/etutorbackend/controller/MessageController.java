package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.message.MessagePayloadRequest;
import com.example.etutorbackend.model.payload.message.MessagePayloadResponse;
import com.example.etutorbackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MessageController {
    private final MessageService messageService;
    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody @Valid MessagePayloadRequest messagePayloadRequest) {
        return ResponseEntity.status(CREATED)
                .body(messageService.createMessage(messagePayloadRequest));
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<MessagePayloadResponse>> getMessagesForConversation(@RequestParam Long firstUserId,
                                                                                   @RequestParam Long secondUserId) {
        return ResponseEntity.status(OK)
                .body(messageService.findAllMessagesForConversationByIds(firstUserId, secondUserId));
    }
}
