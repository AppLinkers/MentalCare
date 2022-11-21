package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class TestResultReadRes {

    private LocalDate date;

    private Double avg;

    private List<DiagnoseResultReadOfTestResultRes> diagnoseResultList;
}
