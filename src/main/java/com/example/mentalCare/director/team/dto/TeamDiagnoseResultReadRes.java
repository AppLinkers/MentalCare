package com.example.mentalCare.director.team.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamDiagnoseResultReadRes {

    private String title;

    private Double avg;

}
