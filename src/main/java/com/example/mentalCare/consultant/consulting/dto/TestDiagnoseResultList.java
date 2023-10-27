package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TestDiagnoseResultList {

    private String title;

    private Double avg;

    private List<QuestionResultReadOfDiagnoseResult> questionResultList;

    private List<PlayerInfoAndAvgScore> worst6PlayerList;
}
