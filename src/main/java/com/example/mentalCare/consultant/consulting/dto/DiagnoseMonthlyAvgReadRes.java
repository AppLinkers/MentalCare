package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiagnoseMonthlyAvgReadRes {

    private String yearMonth;

    private Double avg;
}
