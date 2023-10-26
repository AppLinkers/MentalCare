package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DiagnoseResultDateRes {

    private LocalDate date;

    private Integer count;

    private List<DiagnoseResultReadRes> diagnoseResultReadResList;
}
