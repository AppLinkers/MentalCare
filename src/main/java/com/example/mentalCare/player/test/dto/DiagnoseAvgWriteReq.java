package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiagnoseAvgWriteReq {

    private Long id;

    private Integer avg;

    @Builder
    public DiagnoseAvgWriteReq(Long id, Integer avg) {
        this.id = id;
        this.avg = avg;
    }
}
