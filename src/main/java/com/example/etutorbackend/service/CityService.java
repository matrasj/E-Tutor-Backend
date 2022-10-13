package com.example.etutorbackend.service;

import com.example.etutorbackend.mapper.CityPayloadMapper;
import com.example.etutorbackend.model.entity.City;
import com.example.etutorbackend.model.payload.city.CityPayload;
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

        List<String> citiesWithQuantitiesSorted = cityRepository.findCitiesOrderByAddsQuantityWithLimit(recordsQuantity);

        citiesWithQuantitiesSorted
                .forEach(cityWithQuantity -> {
                    String[] cityAndQuantity = cityWithQuantity.split(",");
                    cityQuantityPayloads.add(
                            new CityQuantityPayload(
                                    cityAndQuantity[0],
                                    Integer.parseInt(cityAndQuantity[1])
                            )
                    );
                });

        return cityQuantityPayloads;
    }

    public List<CityPayload> findAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(CityPayloadMapper::mapToCityPayload)
                .toList();
    }
}
