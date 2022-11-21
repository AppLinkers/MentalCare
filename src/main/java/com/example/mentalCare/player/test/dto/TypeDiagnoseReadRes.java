package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class TypeDiagnoseReadRes {

    private Long id;

    private String title;

    private String iconUrl;

    private List<QuestionReadRes> questionReadResList;

    /**
     * 검사 답변지 생성
     */
    public DiagnoseWriteReq typeDiagnoseReadResToDiagnoseWriteReq() {

        List<QuestionWriteReq> questionWriteReqList = new ArrayList<>();
        for (QuestionReadRes questionReadRes : this.questionReadResList) {
            QuestionWriteReq questionWriteReq = questionReadRes.questionReadResToQuestionWriteReq();
            questionWriteReqList.add(questionWriteReq);
        }


        return DiagnoseWriteReq.builder()
                .id(this.id)
                .questionWriteReqList(questionWriteReqList)
                .build();
    }
}
