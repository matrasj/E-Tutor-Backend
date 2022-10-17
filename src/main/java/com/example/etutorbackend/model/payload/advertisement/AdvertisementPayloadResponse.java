package com.example.etutorbackend.model.payload.advertisement;

import com.example.etutorbackend.model.entity.AdvertisementType;
import com.example.etutorbackend.model.payload.availibility.AvailabilityPayload;
import com.example.etutorbackend.model.payload.user.UserPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementPayloadResponse {
    private Long id;
    private UserPayload userPayload;
    private String subjectName;
    private BigDecimal price;
    private int minutesDuration;
    private List<String> placesNames = new ArrayList<>();
    private String shortDescription;
    private String content;
    private String cityName;
    private List<AvailabilityPayload> availabilityPayloads = new ArrayList<>();
    private List<String> lessonRanges = new ArrayList<>();
    private AdvertisementType advertisementType;
    public int reviewsQuantity;
    private BigDecimal ratingAverage;
    private Date createdAt;
}
