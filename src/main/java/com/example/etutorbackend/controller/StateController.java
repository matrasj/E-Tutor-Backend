package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.state.StatePayloadResponse;
import com.example.etutorbackend.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/states")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StateController {
    private final StateService stateService;

    @GetMapping("/states-cities-quantities")
    public ResponseEntity<List<StatePayloadResponse>> getStatesWithCitiesAndQuantities() {
        return ResponseEntity.status(OK)
                .body(stateService.findStatesWithCitiesAndQuantities());
    }
}
