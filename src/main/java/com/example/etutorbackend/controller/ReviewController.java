package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.review.ReviewPayload;
import com.example.etutorbackend.model.payload.review.ReviewPayloadRequest;
import com.example.etutorbackend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/advertisement/{advertisementId}")
    public ResponseEntity<List<ReviewPayload>> getReviewsByAdvertisement(@PathVariable Long advertisementId) {
        return ResponseEntity.status(OK)
                .body(reviewService.findReviewsByAdvertisementId(advertisementId));
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody ReviewPayloadRequest reviewPayloadRequest) {
        return ResponseEntity.status(CREATED)
                .body(reviewService.createReviewForAdvertisement(reviewPayloadRequest));
    }
}

