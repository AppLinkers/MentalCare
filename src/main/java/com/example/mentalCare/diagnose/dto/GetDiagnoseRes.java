package com.example.mentalCare.diagnose.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetDiagnoseRes {

    private Long diagnoseId;

    private String title;

    private List<GetQuestionRes> questionResList;
}