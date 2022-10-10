package com.example.etutorbackend.service;

import com.example.etutorbackend.mapper.SubjectSearchPayloadMapper;
import com.example.etutorbackend.model.entity.Subject;
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
    private static final long ENTRIES_NUMBER = 16;
    private final SubjectRepository subjectRepository;
    private final AdvertisementRepository advertisementRepository;
    public List<SubjectSearchPayload> findAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectSearchPayloadMapper::mapToSubjectSearchPayloadMapper)
                .collect(Collectors.toList());
    }

    public Map<String, Integer> findEntrySubjectsWithQuantities() {
        Map<String, Integer> subjectsWithQuantities  = new LinkedHashMap<>();


        List<Subject> subjects = subjectRepository.findAll()
                .stream()
                .sorted(((o1, o2) -> o2.getAdvertisements().size() - o1.getAdvertisements().size()))
                .limit(ENTRIES_NUMBER)
                .toList();

        subjects.forEach((subject -> subjectsWithQuantities.put(
                SubjectSearchPayloadMapper.mapToSubjectSearchPayloadMapper(subject).getName(),
                advertisementRepository.countAdvertisementBySubject(subject)
                )));

//        List<Map.Entry<String, Integer> > list
//                = new ArrayList<>(
//                subjectsWithQuantities.entrySet());
//
//        // Comparing two entries by value
//        list.sort((entry1, entry2) -> {
//
//            // Subtracting the entries
//            return entry2.getValue()
//                    - entry1.getValue();
//        });

        return subjectsWithQuantities;
    }


}
