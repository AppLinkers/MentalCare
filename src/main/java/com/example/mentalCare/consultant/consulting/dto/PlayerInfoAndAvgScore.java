package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlayerInfoAndAvgScore {

    private Long playerId;

    private String name;

    private Double score;
}
