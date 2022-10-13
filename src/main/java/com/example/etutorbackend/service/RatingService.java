package com.example.etutorbackend.service;

import com.example.etutorbackend.model.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    public static BigDecimal calculateAverage(List<Review> reviews) {
        int reviewsQuantity = reviews.size();
        if (reviewsQuantity == 0) return BigDecimal.ZERO;

        int ratingSum =
                reviews
                        .stream()
                        .mapToInt(review -> review.getRatingValue().getRatingValue())
                        .sum();

        double average = (double)ratingSum / reviewsQuantity;
        return BigDecimal.valueOf(average  * 10L);

    }
}
