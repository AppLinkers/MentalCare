package com.example.mentalCare.player.profile.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PlayerProfileUpdateRes {

    private String name;

    private String imgUrl;

    private String teamCode;

    private LocalDate nextMatchDate;

    private String position;
}
