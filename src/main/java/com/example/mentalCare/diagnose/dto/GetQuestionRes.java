package com.example.mentalCare.diagnose.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetQuestionRes {

    private Long questionId;

    private String questionContext;

}
