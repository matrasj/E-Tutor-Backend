package com.example.etutorbackend.service;

import com.example.etutorbackend.exception.AdvertisementNotFoundException;
import com.example.etutorbackend.exception.UserNotFoundException;
import com.example.etutorbackend.mapper.MessagePayloadResponseMapper;
import com.example.etutorbackend.model.entity.Advertisement;
import com.example.etutorbackend.model.entity.Message;
import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.message.MessagePayloadRequest;
import com.example.etutorbackend.model.payload.message.MessagePayloadResponse;
import com.example.etutorbackend.repository.AdvertisementRepository;
import com.example.etutorbackend.repository.MessageRepository;
import com.example.etutorbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "Not found author with id %d";
    private static final String RECIPIENT_NOT_FOUND_MESSAGE = "Not found recipient with id %d";
    private static final String ADVERTISEMENT_NOT_FOUND_MESSAGE = "Not found advertisement with id %d";
    private static final String SUCCESSFULLY_MESSAGE_CREATION = "Successfully created message";
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;



    @Transactional
    public String createMessage(MessagePayloadRequest messagePayloadRequest) {
        User sender = userRepository.findById(messagePayloadRequest.getSenderId())
                .orElseThrow(() -> new UserNotFoundException(String.format(AUTHOR_NOT_FOUND_MESSAGE, messagePayloadRequest.getSenderId())));

        User receiver = userRepository.findById(messagePayloadRequest.getReceiverId())
                .orElseThrow(() -> new UserNotFoundException(String.format(RECIPIENT_NOT_FOUND_MESSAGE, messagePayloadRequest.getReceiverId())));

        Message message = Message.builder()
                .subject(messagePayloadRequest.getSubject())
                .content(messagePayloadRequest.getContent())
                .sender(sender)
                .receiver(receiver)
                .build();

        messageRepository.save(message);

        sender.getMessagesSent().add(message);
        receiver.getMessagesReceived().add(message);

        userRepository.save(sender);
        userRepository.save(receiver);

        return SUCCESSFULLY_MESSAGE_CREATION;
    }


}
