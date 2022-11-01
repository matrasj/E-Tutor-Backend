package com.example.etutorbackend.service;

import com.example.etutorbackend.mapper.CityPayloadMapper;
import com.example.etutorbackend.model.payload.city.CityPayload;
import com.example.etutorbackend.model.payload.city.CityQuantityPayload;
import com.example.etutorbackend.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<CityQuantityPayload> findCitiesWithQuantities(int recordsQuantity) {
        List<CityQuantityPayload> cityQuantityPayloads = new ArrayList<>();

        List<String> citiesWithQuantitiesSorted = cityRepository.findCitiesOrderByAddsQuantityWithLimit(recordsQuantity);

        mapCitiesWithQuantitiesAsStringToCityQuantityPayloads(cityQuantityPayloads, citiesWithQuantitiesSorted);

        return cityQuantityPayloads;
    }

    public List<CityPayload> findAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(CityPayloadMapper::mapToCityPayload)
                .toList();
    }

    public List<CityQuantityPayload> findAllCitiesWithQuantities() {
        List<CityQuantityPayload> cityQuantityPayloads = new ArrayList<>();

        List<String> citiesWithQuantitiesSorted = cityRepository.findAllCitiesWithAddsQuantities();

        mapCitiesWithQuantitiesAsStringToCityQuantityPayloads(cityQuantityPayloads, citiesWithQuantitiesSorted);

        return cityQuantityPayloads;
    }

    private static void mapCitiesWithQuantitiesAsStringToCityQuantityPayloads(List<CityQuantityPayload> cityQuantityPayloads, List<String> citiesWithQuantitiesSorted) {
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
    }
}
