package com.example.etutorbackend.service;

import com.example.etutorbackend.exception.AdvertisementNotFoundException;
import com.example.etutorbackend.exception.RatingValueNotFoundException;
import com.example.etutorbackend.exception.UserNotFoundException;
import com.example.etutorbackend.mapper.ReviewPayloadMapper;
import com.example.etutorbackend.model.entity.Advertisement;
import com.example.etutorbackend.model.entity.RatingValue;
import com.example.etutorbackend.model.entity.Review;
import com.example.etutorbackend.model.entity.User;
import com.example.etutorbackend.model.payload.review.ReviewPayload;
import com.example.etutorbackend.model.payload.review.ReviewPayloadRequest;
import com.example.etutorbackend.repository.AdvertisementRepository;
import com.example.etutorbackend.repository.ReviewRepository;
import com.example.etutorbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private static final String ADVERTISEMENT_NOT_FOUND_MESSAGE = "Not found advertisement with id %d";
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with id %d";
    private static final String INVALID_STARTS_NUMBER = "Invalid number of stats: %d";
    private static final String SUCCESSFULLY_REVIEW_CREATION = "Successfully created review";
    private final ReviewRepository reviewRepository;
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    public List<ReviewPayload> findReviewsByAdvertisementId(Long advertisementId) {
        return reviewRepository.findByAdvertisementId(advertisementId)
                .stream()
                .map(ReviewPayloadMapper::mapToReviewPayload)
                .collect(Collectors.toList());
    }

    @Transactional
    public String createReviewForAdvertisement(ReviewPayloadRequest reviewPayloadRequest) {
        Advertisement advertisement = advertisementRepository.findById(reviewPayloadRequest.getAdvertisementId())
                .orElseThrow(() -> new AdvertisementNotFoundException(String.format(ADVERTISEMENT_NOT_FOUND_MESSAGE, reviewPayloadRequest.getAdvertisementId())));

        User author = userRepository.findById(reviewPayloadRequest.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, reviewPayloadRequest.getAuthorId())));

        Review review = Review.builder()
                .content(reviewPayloadRequest.getContent())
                .ratingValue(Arrays.stream(RatingValue.values()).filter((ratingValue -> ratingValue.getRatingValue() == reviewPayloadRequest.getStarsNumber())).findFirst()
                        .orElseThrow(() -> new RatingValueNotFoundException(String.format(INVALID_STARTS_NUMBER, reviewPayloadRequest.getStarsNumber()))))
                .user(author)
                .advertisement(advertisement)
                .build();

        reviewRepository.save(review);

        author.getReviews().add(review);
        advertisement.getReviews().add(review);

        userRepository.save(author);
        advertisementRepository.save(advertisement);

        return SUCCESSFULLY_REVIEW_CREATION;

    }
}
