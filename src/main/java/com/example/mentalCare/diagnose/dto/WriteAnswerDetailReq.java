package com.example.mentalCare.diagnose.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WriteAnswerDetailReq {

    private Long questionId;

    private Integer answer;

    public WriteAnswerDetailReq(Long questionId) {
        this.questionId = questionId;
    }
}
