package com.example.etutorbackend.model.payload.advertisement;

import com.example.etutorbackend.model.entity.AdvertisementType;
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
public class AdvertisementLightPayload {
    private Long id;
    private String profileImagePath;
    private String subjectName;
    private BigDecimal price;
    private int minutesDuration;
    private List<String> placesNames = new ArrayList<>();
    public int reviewsQuantity;
    private AdvertisementType advertisementType;
}
