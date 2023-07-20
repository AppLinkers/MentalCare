package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DiagnoseResultReadOfTestResultRes implements Comparable<DiagnoseResultReadOfTestResultRes> {

    private String title;

    int diagId;

    private Double avg;

    List<QuestionResultReadOfDiagnoseResult> questionResultList;


    @Override
    public int compareTo(DiagnoseResultReadOfTestResultRes o) {
        if (this.diagId != o.getDiagId()) {
            return this.diagId - o.getDiagId();
        }
        return this.title.compareTo(o.getTitle());
    }
}
