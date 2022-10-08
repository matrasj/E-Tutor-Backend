package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.subject.SubjectSearchPayload;
import com.example.etutorbackend.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    @GetMapping
    public ResponseEntity<List<SubjectSearchPayload>> getAllSubjects() {
        return ResponseEntity.status(OK)
                .body(subjectService.findAllSubjects());
    }
}
