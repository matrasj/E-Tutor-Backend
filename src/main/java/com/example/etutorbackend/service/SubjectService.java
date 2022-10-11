package com.example.etutorbackend.service;

import com.example.etutorbackend.mapper.SubjectSearchPayloadMapper;
import com.example.etutorbackend.model.entity.Subject;
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


        List<Subject> subjects = subjectRepository.findAll()
                .stream()
                .sorted(((o1, o2) -> o2.getAdvertisements().size() - o1.getAdvertisements().size()))
                .limit(recordsQuantity)
                .toList();

        subjects.forEach((subject -> {
            subjectsWithQuantities.add(
                    SubjectQuantityPayload.builder()
                            .subjectName(subject.getName())
                            .addsQuantity(subject.getAdvertisements().size())
                            .build()
            );
        }));


        return subjectsWithQuantities;
    }


}
