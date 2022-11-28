package com.example.mentalCare.player.profile.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlayerProfileReadRes {

    private Long id;

    private String name;

    private String imgUrl;

    private Role role;

    private String teamName;

    private String nextMatchDDay;

    private String position;
}
