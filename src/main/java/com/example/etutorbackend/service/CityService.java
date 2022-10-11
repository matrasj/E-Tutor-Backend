package com.example.etutorbackend.service;

import com.example.etutorbackend.model.entity.City;
import com.example.etutorbackend.model.payload.city.CityQuantityPayload;
import com.example.etutorbackend.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<CityQuantityPayload> findCitiesWithQuantities(int recordsQuantity) {
        List<CityQuantityPayload> cityQuantityPayloads = new ArrayList<>();

        List<City> cities = cityRepository.findAll()
                .stream()
                .sorted(((o1, o2) -> o2.getAdvertisements().size() - o1.getAdvertisements().size()))
                .limit(recordsQuantity)
                .toList();

        cities.forEach((city -> cityQuantityPayloads.add(
                CityQuantityPayload.builder()
                        .cityName(city.getName())
                        .addsQuantity(city.getAdvertisements().size())
                        .build()
        )));

        return cityQuantityPayloads;
    }
}
