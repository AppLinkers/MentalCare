package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TestDiagnoseResultReadRes {


    private String title;

    private Double avg;

    public TestDiagnoseResultReadRes(String title, Double avg) {
        this.title = title;
        this.avg = avg;
    }
}
