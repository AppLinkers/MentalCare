package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DiagnoseResultReadOfTestResultRes {

    private String title;

    private Double avg;

    private LocalDate date;

    List<QuestionResultReadOfDiagnoseResult> questionResultList;
}
