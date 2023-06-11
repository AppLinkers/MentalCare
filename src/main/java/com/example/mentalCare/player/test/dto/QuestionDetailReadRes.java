package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionDetailReadRes {

    private Long id;

    private Integer weight;

    private String optionValue;
}
