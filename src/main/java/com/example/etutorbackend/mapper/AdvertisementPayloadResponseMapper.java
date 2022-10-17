package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.Advertisement;
import com.example.etutorbackend.model.entity.LessonRange;
import com.example.etutorbackend.model.entity.Place;
import com.example.etutorbackend.model.payload.advertisement.AdvertisementPayloadResponse;
import com.example.etutorbackend.service.RatingService;

public class AdvertisementPayloadResponseMapper {
    public static AdvertisementPayloadResponse mapToAdvertisementPayloadResponse(Advertisement advertisement) {
        return AdvertisementPayloadResponse.builder()
                .id(advertisement.getId())
                .userPayload(UserPayloadMapper.mapToUserPayload(advertisement.getUser()))
                .subjectName(advertisement.getSubject().getName())
                .price(advertisement.getPrice())
                .minutesDuration(advertisement.getMinutesDuration())
                .placesNames(advertisement.getPlaces()
                        .stream()
                        .map(Place::getName)
                        .toList())
                .shortDescription(advertisement.getShortDesc())
                .content(advertisement.getContent())
                .cityName(advertisement.getCity().getName())
                .availabilityPayloads(advertisement.getAvailabilities()
                        .stream()
                        .map(AvailabilityPayloadMapper::mapToAvailabilityPayload)
                        .toList())
                .lessonRanges(advertisement.getLessonRanges()
                        .stream()
                        .map(LessonRange::getName)
                        .toList())
                .advertisementType(advertisement.getAdvertisementType())
                .reviewsQuantity(advertisement.getReviews().size())
                .ratingAverage(RatingService.calculateAverage(advertisement.getReviews()))
                .createdAt(advertisement.getCreatedAt())
                .build();
    }
}
