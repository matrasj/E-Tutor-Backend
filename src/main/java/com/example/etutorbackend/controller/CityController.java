package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.city.CityPayload;
import com.example.etutorbackend.model.payload.city.CityQuantityPayload;
import com.example.etutorbackend.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CityController {
    private final CityService cityService;
    @GetMapping("/cities-quantities")
    public ResponseEntity<List<CityQuantityPayload>> getCitiesWithAddsQuantitiesWithLimit(@RequestParam int recordsQuantity) {
        return ResponseEntity.status(OK)
                .body(cityService.findCitiesWithQuantities(recordsQuantity));
    }

    @GetMapping("/cities-quantities/all")
    public ResponseEntity<List<CityQuantityPayload>> getAllCitiesWithAddsQuantities() {
        return ResponseEntity.status(OK)
                .body(cityService.findAllCitiesWithQuantities());
    }

    @GetMapping
    public ResponseEntity<List<CityPayload>> getAllCities() {
        return ResponseEntity.status(OK)
                .body(cityService.findAllCities());
    }

}
