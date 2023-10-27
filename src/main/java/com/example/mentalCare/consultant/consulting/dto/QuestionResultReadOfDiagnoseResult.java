package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResultReadOfDiagnoseResult {

    private String keyword;

    private Double answer;
}
