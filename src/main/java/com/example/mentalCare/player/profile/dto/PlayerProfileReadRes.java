package com.example.mentalCare.player.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlayerProfileReadRes {

    private String name;

    private String imgUrl;

    private String teamName;

    private String nextMatchDDay;

    private String position;
}
