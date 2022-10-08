package com.example.etutorbackend.mapper;

import com.example.etutorbackend.model.entity.Subject;
import com.example.etutorbackend.model.payload.subject.SubjectSearchPayload;

public class SubjectSearchPayloadMapper {
    public static SubjectSearchPayload mapToSubjectSearchPayloadMapper(Subject subject) {
        return SubjectSearchPayload.builder()
                .id(subject.getId())
                .name(subject.getName())
                .build();
    }
}
