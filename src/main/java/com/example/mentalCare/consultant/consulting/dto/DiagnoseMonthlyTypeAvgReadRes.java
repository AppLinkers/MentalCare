package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DiagnoseMonthlyTypeAvgReadRes {

    private String title;

    private List<DiagnoseMonthlyAvgReadRes> monthlyResultList;
}
