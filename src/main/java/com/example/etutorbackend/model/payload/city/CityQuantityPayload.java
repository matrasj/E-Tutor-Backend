package com.example.etutorbackend.model.payload.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CityQuantityPayload {
    private String cityName;
    private int addsQuantity;
}
