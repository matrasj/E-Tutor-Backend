package com.example.etutorbackend.model.payload.advertisement;

import com.example.etutorbackend.model.entity.AdvertisementType;
import com.example.etutorbackend.model.payload.availibility.AvailabilityPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementPayloadRequest {
    private Long authorId;
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
    private String authorImageLink;

}
