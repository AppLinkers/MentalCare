package com.example.mentalCare.consultant.consulting.dto;

import lombok.Getter;

@Getter
public class DiagnoseResultWithAnswerIdReadRes {

    private Double avg;

    private Long playerId;

    private Long answerId;

    private String playerName;

    private String playerImgUrl;

    public DiagnoseResultWithAnswerIdReadRes(Long answerId, Long playerId, String playerName, String playerImgUrl, Double avg) {
        this.avg = avg;
        this.playerId=playerId;
        this.answerId = answerId;
        this.playerName = playerName;
        this.playerImgUrl = playerImgUrl;
    }
}
