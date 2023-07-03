package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestDiagnoseResultReadRes {

    private String title;

    private Double avg;
}
