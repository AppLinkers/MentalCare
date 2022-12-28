package com.example.mentalCare.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamInfoRes {

    private Long id;

    private String name;

    private String teamCode;
}
