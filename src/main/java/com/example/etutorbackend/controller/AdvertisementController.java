package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.advertisement.AdvertisementPayloadRequest;
import com.example.etutorbackend.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    @PostMapping
    public ResponseEntity<String> createAdvertisement(@RequestBody AdvertisementPayloadRequest advertisementPayloadRequest) {
        return ResponseEntity.status(CREATED)
                .body(advertisementService.createAdvertisement(advertisementPayloadRequest));
    }
}
