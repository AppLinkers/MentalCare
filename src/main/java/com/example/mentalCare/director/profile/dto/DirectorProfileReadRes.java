package com.example.mentalCare.director.profile.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectorProfileReadRes {

    private Long id;

    private String name;

    private String imgUrl;

    private Role role;

    private String teamName;
}
