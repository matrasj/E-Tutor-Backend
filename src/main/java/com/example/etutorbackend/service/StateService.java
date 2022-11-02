package com.example.etutorbackend.service;

import com.example.etutorbackend.model.entity.State;
import com.example.etutorbackend.model.payload.city.CityQuantityPayload;
import com.example.etutorbackend.model.payload.state.StatePayloadResponse;
import com.example.etutorbackend.repository.CityRepository;
import com.example.etutorbackend.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;


    public List<StatePayloadResponse> findStatesWithCitiesAndQuantities() {
        List<StatePayloadResponse> statePayloadResponses = new ArrayList<>();
        stateRepository.findAll()
                .forEach((state -> {
                    List<CityQuantityPayload> quantityPayloads = new ArrayList<>();

                    cityRepository.findCitiesByStateWithAddsQuantity(state.getId())
                            .forEach((cityWithQuantity) -> {
                        String[] cityAndQuantity = cityWithQuantity.split(",");
                                quantityPayloads.add(
                                new CityQuantityPayload(
                                        cityAndQuantity[0],
                                        Integer.parseInt(cityAndQuantity[1])
                                )
                        );
                    });

                    StatePayloadResponse statePayloadResponse = StatePayloadResponse.builder()
                            .id(state.getId())
                            .name(state.getName())
                            .citiesWithQuantities(quantityPayloads)
                            .totalAddsQuantity(
                                    stateRepository.citiesNumberForStateId(state.getId())
                            ).build();

                    statePayloadResponses.add(statePayloadResponse);
                }));

        return statePayloadResponses;
    }

    public List<State> findAllStates() {
        return stateRepository.findAll();
    }
}
