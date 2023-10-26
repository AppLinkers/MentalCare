package com.example.mentalCare.consultant.consulting.dto;

import lombok.Getter;

@Getter
public class DiagnoseResultReadRes {

    private Double avg;

    private Long answerId;

    private String playerName;

    private String playerImgUrl;

    public DiagnoseResultReadRes(Long answerId, String playerName, String playerImgUrl, Double avg) {
        this.avg = avg;
        this.answerId = answerId;
        this.playerName = playerName;
        this.playerImgUrl = playerImgUrl;
    }
}
