package com.example.mentalCare.director.team.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamPlayerInfoReadRes {

    private Long id;

    private String imgUrl;

    private String name;

    private Role role;

    private String position;

}
