package com.example.mentalCare.diagnose.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GetAnswerInfoRes {

    private Long answerId;

    private LocalDateTime answerDate;

}
