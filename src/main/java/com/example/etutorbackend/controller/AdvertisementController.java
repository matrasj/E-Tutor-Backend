package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.advertisement.AdvertisementPayloadRequest;
import com.example.etutorbackend.model.payload.advertisement.AdvertisementPayloadResponse;
import com.example.etutorbackend.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{advertisementId}")
    public ResponseEntity<AdvertisementPayloadResponse> getAdvertisement(@PathVariable Long advertisementId) {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementById(advertisementId));
    }
    @GetMapping("/pagination/findByKeyphrase")
    public ResponseEntity<Page<AdvertisementPayloadResponse>> getAdvertisementsByKeyphrase(@RequestParam String keyPhrase,
                                                                                           @RequestParam int pageNumber,
                                                                                           @RequestParam int pageSize) {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementsByKeyphrase(
                        keyPhrase,
                        pageNumber,
                        pageSize));
    }

    @GetMapping("/pagination/findBySubjectName")
    public ResponseEntity<Page<AdvertisementPayloadResponse>> getAdvertisementsBySubjectName(@RequestParam String subjectName,
                                                                                             @RequestParam int pageNumber,
                                                                                             @RequestParam int pageSize) {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementsBySubjectName(
                       pageNumber, pageSize, subjectName
                ));
    }

    @GetMapping("/pagination/findByCityName")
    public ResponseEntity<Page<AdvertisementPayloadResponse>> getAdvertisementsByCityName(@RequestParam String cityName,
                                                                                             @RequestParam int pageNumber,
                                                                                             @RequestParam int pageSize) {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementsByCityName(
                        pageNumber, pageSize, cityName
                ));
    }

    @GetMapping("/pagination/findByType")
    public ResponseEntity<Page<AdvertisementPayloadResponse>> getAdvertisementsByType(@RequestParam String type,
                                                                                      @RequestParam int pageNumber,
                                                                                      @RequestParam int pageSize) {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementsByAdvertisementType(type, pageNumber, pageSize));
    }

    @GetMapping("/pagination/findByKeyphraseAndType")
    public ResponseEntity<Page<AdvertisementPayloadResponse>> getAdvertisementsByKeyphraseAndType(@RequestParam String keyPhrase,
                                                                                                  @RequestParam String type,
                                                                                                  @RequestParam int pageNumber,
                                                                                                  @RequestParam int pageSize) {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementsByKeyphraseAndType(
                        keyPhrase,
                        type,
                        pageNumber,
                        pageSize
                ));
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<List<AdvertisementPayloadResponse>> getAdvertisementsByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementsByUserId(userId));
    }

    @GetMapping("/limit/{limit}")
    public ResponseEntity<List<AdvertisementPayloadResponse>> getAdvertisementForHomePage(@PathVariable int limit) {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementForHomePageWithLimit(limit));
    }

    @GetMapping("/totalQuantity")
    public ResponseEntity<Integer> getAdvertisementsTotalQuantity() {
        return ResponseEntity.status(OK)
                .body(advertisementService.findAdvertisementsTotalQuantity());
    }


}

