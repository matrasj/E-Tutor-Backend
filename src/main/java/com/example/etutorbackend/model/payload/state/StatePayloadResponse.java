package com.example.etutorbackend.model.payload.state;

import com.example.etutorbackend.model.payload.city.CityQuantityPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StatePayloadResponse {
    private Long id;
    private String name;
    private List<CityQuantityPayload> citiesWithQuantities = new ArrayList<>();
    private int totalAddsQuantity;
}
