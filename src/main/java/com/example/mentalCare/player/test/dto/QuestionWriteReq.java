package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionWriteReq {

    private Long id;

    private Integer answer;

    @Builder
    public QuestionWriteReq(Long id) {
        this.id = id;
    }
}
