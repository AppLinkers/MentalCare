package com.example.mentalCare.player.profile.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PlayerProfileUpdateRes {

    private Long id;

    private String name;

    private String imgUrl;

    private Role role;

    private String teamCode;

    private LocalDate nextMatchDate;

    private String position;
}
