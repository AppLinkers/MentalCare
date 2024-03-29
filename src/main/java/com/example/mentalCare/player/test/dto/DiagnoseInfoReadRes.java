package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class DiagnoseInfoReadRes {

    private Long id;

    private String title;

    private String iconUrl;
}
