package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionReadRes {

    private Long id;

    private String context;

    /**
     * 질문 답변지 생성
     */
    public QuestionWriteReq questionReadResToQuestionWriteReq() {
        return QuestionWriteReq.builder()
                .id(this.id)
                .build();
    }
}
