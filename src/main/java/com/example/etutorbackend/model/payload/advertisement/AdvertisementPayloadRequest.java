package com.example.etutorbackend.model.payload.advertisement;

import com.example.etutorbackend.model.entity.AdvertisementType;
import com.example.etutorbackend.model.payload.availibility.AvailabilityPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementPayloadRequest {
    private Long authorId;
    @NotBlank(message = "Subject name field is required")
    private String subjectName;
    @NotBlank(message = "Price field is required")
    private BigDecimal price;
    @NotBlank(message = "Minutes duration field is required")
    private int minutesDuration;
    private List<String> placesNames = new ArrayList<>();
    @NotBlank(message = "Short description field is required")
    private String shortDescription;
    @NotBlank(message = "Content description field is required")
    private String content;
    @NotBlank(message = "City name field is required")
    private String cityName;
    private List<AvailabilityPayload> availabilityPayloads = new ArrayList<>();
    private List<String> lessonRanges = new ArrayList<>();
    @NotBlank(message = "Advertisement type field is required")
    private AdvertisementType advertisementType;
    private String authorImageLink;

}
