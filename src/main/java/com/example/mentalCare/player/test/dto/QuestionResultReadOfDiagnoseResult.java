package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResultReadOfDiagnoseResult {

    private String keyword;

    private Integer answer;
}
