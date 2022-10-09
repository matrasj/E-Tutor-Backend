package com.example.etutorbackend.model.payload.advertisement;

import com.example.etutorbackend.model.entity.AdvertisementType;
import com.example.etutorbackend.model.payload.availibility.AvailabilityPayloadRequest;
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
    private String subjectName;
    private BigDecimal price;
    private int minutesDuration;
    private List<String> placesNames = new ArrayList<>();
    private String shortDescription;
    private String content;
    private List<AvailabilityPayloadRequest> availabilityPayloads = new ArrayList<>();
    private List<String> lessonRanges = new ArrayList<>();
    private AdvertisementType advertisementType;

}
