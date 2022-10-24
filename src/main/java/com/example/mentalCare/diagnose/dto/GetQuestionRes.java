package com.example.mentalCare.diagnose.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetQuestionRes {

    private Long questionId;

    private String questionContext;

    public GetQuestionRes(Long questionId, String questionContext){
        this.questionId = questionId;
        this.questionContext = questionContext;
    }

}
