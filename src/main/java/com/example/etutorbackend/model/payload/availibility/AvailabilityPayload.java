package com.example.etutorbackend.model.payload.availibility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityPayload {
    private String dayName;
    private String startHour;
    private String endHour;

}
