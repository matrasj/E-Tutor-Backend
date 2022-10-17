package com.example.etutorbackend.service;

import com.example.etutorbackend.mapper.SubjectSearchPayloadMapper;
import com.example.etutorbackend.model.payload.subject.SubjectQuantityPayload;
import com.example.etutorbackend.model.payload.subject.SubjectSearchPayload;
import com.example.etutorbackend.repository.AdvertisementRepository;
import com.example.etutorbackend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final AdvertisementRepository advertisementRepository;
    public List<SubjectSearchPayload> findAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectSearchPayloadMapper::mapToSubjectSearchPayloadMapper)
                .collect(Collectors.toList());
    }

    public List<SubjectQuantityPayload> findSubjectsWithQuantities(int recordsQuantity) {
        List<SubjectQuantityPayload> subjectsWithQuantities = new ArrayList<>();


        List<String> subjectsWithQuantitiesSorted = recordsQuantity > 0
                ? subjectRepository.findSubjectsOrderByAddsQuantityWithLimit(recordsQuantity)
                : subjectRepository.findSubjectsOrderByAddsQuantity();

        subjectsWithQuantitiesSorted
                .forEach(subjectWithQuantity -> {
                    String[] cityAndQuantity = subjectWithQuantity.split(",");
                    subjectsWithQuantities.add(
                            new SubjectQuantityPayload(
                                    cityAndQuantity[0],
                                    Integer.parseInt(cityAndQuantity[1])
                            )
                    );
                });

        return subjectsWithQuantities;
    }
}
