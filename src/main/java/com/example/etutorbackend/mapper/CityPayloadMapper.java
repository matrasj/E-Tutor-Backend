package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.City;
import com.example.etutorbackend.model.payload.city.CityPayload;

public class CityPayloadMapper {
    public static CityPayload mapToCityPayload(City city) {
        return CityPayload.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }
}
