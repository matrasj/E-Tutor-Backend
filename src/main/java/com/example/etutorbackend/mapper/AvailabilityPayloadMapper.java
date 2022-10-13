package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.Availability;
import com.example.etutorbackend.model.payload.availibility.AvailabilityPayload;

public class AvailabilityPayloadMapper {
    public static AvailabilityPayload mapToAvailabilityPayload(Availability availability) {
        return AvailabilityPayload.builder()
                .dayName(availability.getDay())
                .startHour(availability.getHourStart())
                .endHour(availability.getHourEnd())
                .build();
    }
}
