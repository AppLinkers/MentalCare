package com.example.mentalCare.player.test.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDiagnoseWriteReq {

    private List<DiagnoseAvgWriteReq> answerDiagnoseWriteReq;
}
