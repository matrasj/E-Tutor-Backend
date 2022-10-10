package com.example.etutorbackend.service;

import com.example.etutorbackend.mapper.SubjectSearchPayloadMapper;
import com.example.etutorbackend.model.entity.Subject;
import com.example.etutorbackend.model.payload.subject.SubjectSearchPayload;
import com.example.etutorbackend.repository.AdvertisementRepository;
import com.example.etutorbackend.repository.SubjectRepository;
import com.example.etutorbackend.util.MapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private static final long ENTRIES_NUMBER = 16;
    private final SubjectRepository subjectRepository;
    private final AdvertisementRepository advertisementRepository;
    public List<SubjectSearchPayload> findAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectSearchPayloadMapper::mapToSubjectSearchPayloadMapper)
                .collect(Collectors.toList());
    }

    public Map<SubjectSearchPayload, Integer> findEntrySubjectsWithQuantities() {
        Map<SubjectSearchPayload, Integer> subjectsWithQuantities  = new LinkedHashMap<>();


        List<Subject> subjects = subjectRepository.findAll()
                .stream()
                .sorted(((o1, o2) -> o2.getAdvertisements().size() - o1.getAdvertisements().size()))
                .limit(ENTRIES_NUMBER)
                .toList();

        subjects.forEach((subject -> subjectsWithQuantities.put(
                SubjectSearchPayloadMapper.mapToSubjectSearchPayloadMapper(subject),
                advertisementRepository.countAdvertisementBySubject(subject)
                )));

        MapUtil.sortByValue(subjectsWithQuantities);

        return subjectsWithQuantities;
    }


}
