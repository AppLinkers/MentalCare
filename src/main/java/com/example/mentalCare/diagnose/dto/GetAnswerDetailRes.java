package com.example.mentalCare.diagnose.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAnswerDetailRes {

    private String questionKeyword;

    private Integer answer;

}
