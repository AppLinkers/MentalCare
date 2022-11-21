package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiagnoseInfoReadRes {

    private Long id;

    private String title;

    private String iconUrl;
}
