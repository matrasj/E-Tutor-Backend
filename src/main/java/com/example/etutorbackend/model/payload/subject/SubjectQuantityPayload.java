package com.example.etutorbackend.model.payload.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SubjectQuantityPayload {
    private String subjectName;
    private int addsQuantity;
}
