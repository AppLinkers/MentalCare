package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DiagnoseResultDiagnoseReadRes {

    private String title;

    private List<DiagnoseResultWithPlayerIdReadRes> diagnoseResultReadResList;
}
