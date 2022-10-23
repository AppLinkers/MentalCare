package com.example.mentalCare.diagnose.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class GetAnswerRes {

    private String playerName;

    private Integer playerAge;

    private String playerPosition;

    private LocalDateTime answerDate;

    private Double totalAverage;

    private List<GetAnswerDiagnoseRes> diagnoseResList;

}