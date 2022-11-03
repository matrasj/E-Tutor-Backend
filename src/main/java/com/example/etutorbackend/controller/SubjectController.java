package com.example.etutorbackend.controller;

import com.example.etutorbackend.model.payload.subject.SubjectQuantityPayload;
import com.example.etutorbackend.model.payload.subject.SubjectSearchPayload;
import com.example.etutorbackend.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SubjectController {
    private final SubjectService subjectService;
    @GetMapping
    public ResponseEntity<List<SubjectSearchPayload>> getAllSubjects() {
        return ResponseEntity.status(OK)
                .body(subjectService.findAllSubjects());
    }

    @GetMapping("/subjects-quantities")
    public ResponseEntity<List<SubjectQuantityPayload>> getSubjectsWithQuantities(@RequestParam int recordsQuantity) {
        return ResponseEntity.status(OK)
                .body(subjectService.findSubjectsWithQuantities(recordsQuantity));
    }






}
