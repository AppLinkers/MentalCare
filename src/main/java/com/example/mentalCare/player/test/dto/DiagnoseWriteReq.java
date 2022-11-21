package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DiagnoseWriteReq {

    private Long id;

    private List<QuestionWriteReq> questionWriteReqList;

    @Builder
    public DiagnoseWriteReq(Long id, List<QuestionWriteReq> questionWriteReqList) {
        this.id = id;
        this.questionWriteReqList = questionWriteReqList;
    }
}
