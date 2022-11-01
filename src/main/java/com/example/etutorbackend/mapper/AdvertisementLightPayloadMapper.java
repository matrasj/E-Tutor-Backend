package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.Advertisement;
import com.example.etutorbackend.model.entity.Place;
import com.example.etutorbackend.model.payload.advertisement.AdvertisementLightPayload;

public class AdvertisementLightPayloadMapper {
    public static AdvertisementLightPayload mapTopAdvertisementLightPayload(Advertisement advertisement) {
        return AdvertisementLightPayload.builder()
                .id(advertisement.getId())
                .profileImagePath(advertisement.getUser().getProfileImagePath())
                .subjectName(advertisement.getSubject().getName())
                .price(advertisement.getPrice())
                .minutesDuration(advertisement.getMinutesDuration())
                .placesNames(advertisement
                        .getPlaces()
                        .stream()
                        .map(Place::getName)
                        .toList())
                .reviewsQuantity(advertisement.getReviews().size())
                .advertisementType(advertisement.getAdvertisementType())
                .build();
    }
}
