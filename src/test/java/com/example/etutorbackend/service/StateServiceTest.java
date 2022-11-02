package com.example.etutorbackend.service;

import com.example.etutorbackend.repository.StateRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class StateServiceTest {
    @Mock
    private StateRepository stateRepository;

    @Mock
    private StateService stateService;
    private AutoCloseable autoCloseable;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findStatesWithCitiesAndQuantities() {
    }

    @Test
    void findAllStates() {
        stateRepository.findAll();
        Mockito.verify(stateRepository).deleteAll();
    }
}