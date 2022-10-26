package com.example.mentalCare.diagnose.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class GetAnswerDiagnoseRes {

    private String diagnoseTitle;

    private Double diagnoseAverage;

    private LocalDateTime diagnoseAnswerDate;

    private List<GetAnswerDetailRes> answerDetailResList;

}
