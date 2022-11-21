package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionWriteReq {

    private Long id;

    private Integer answer;

    @Builder
    public QuestionWriteReq(Long id, Integer answer) {
        this.answer=answer;
        this.id = id;
    }
}
