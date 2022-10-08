package com.example.etutorbackend.service;

import com.example.etutorbackend.mapper.SubjectSearchPayloadMapper;
import com.example.etutorbackend.model.payload.subject.SubjectSearchPayload;
import com.example.etutorbackend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    public List<SubjectSearchPayload> findAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectSearchPayloadMapper::mapToSubjectSearchPayloadMapper)
                .collect(Collectors.toList());
    }
}
