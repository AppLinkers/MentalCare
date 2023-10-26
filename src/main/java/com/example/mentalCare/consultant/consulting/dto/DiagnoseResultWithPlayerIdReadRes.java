package com.example.mentalCare.consultant.consulting.dto;

import com.example.mentalCare.player.test.dto.DiagnoseResultReadOfTestResultRes;
import lombok.Getter;

@Getter
public class DiagnoseResultWithPlayerIdReadRes implements Comparable<DiagnoseResultWithPlayerIdReadRes>{

    private Double avg;

    private Long playerId;

    private String playerName;

    private String playerImgUrl;

    public DiagnoseResultWithPlayerIdReadRes(Double avg, Long playerId, String playerName, String playerImgUrl) {
        this.avg = avg;
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerImgUrl = playerImgUrl;
    }

    @Override
    public int compareTo(DiagnoseResultWithPlayerIdReadRes o) {
        return (int) (o.avg - this.avg);
    }
}
