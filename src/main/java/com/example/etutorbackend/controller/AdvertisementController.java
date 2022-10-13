package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.advertisement.AdvertisementPayloadRequest;
import com.example.etutorbackend.model.payload.advertisement.AdvertisementPayloadResponse;
import com.example.etutorbackend.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    @PostMapping
    public ResponseEntity<String> createAdvertisement(@RequestBody AdvertisementPayloadRequest advertisementPayload) {
        return ResponseEntity.status(CREATED)
                .body(advertisementService.createAdvertisement(advertisementPayload));
    }

    @GetMapping("/pagination/findByShortTitleKeyphraseContaining")
    public ResponseEntity<Page<AdvertisementPayloadResponse>> getAdvertisementsByKeyphraseWithPagination(@RequestParam String keyPhrase,
                                                                                                         @RequestParam int pageNumber,
                                                                                                         @RequestParam int pageSize
                                                                                                        ) {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementsByKeyphraseWithPagination(keyPhrase, pageNumber, pageSize));
    }
}
