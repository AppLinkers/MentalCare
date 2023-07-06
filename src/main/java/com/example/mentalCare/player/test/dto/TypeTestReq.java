package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypeTestReq {

    private AnswerWriteReq answerWriteReq;

    private AnswerDiagnoseWriteReq answerDiagnoseWriteReq;

    @Builder
    public TypeTestReq(AnswerWriteReq answerWriteReq, AnswerDiagnoseWriteReq answerDiagnoseWriteReq) {
        this.answerWriteReq = answerWriteReq;
        this.answerDiagnoseWriteReq = answerDiagnoseWriteReq;
    }
}
