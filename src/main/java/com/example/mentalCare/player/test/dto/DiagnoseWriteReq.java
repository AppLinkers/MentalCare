package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DiagnoseWriteReq {

    private Long id;

    private List<QuestionWriteReq> questionWriteReqList;
}
