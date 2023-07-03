package com.example.mentalCare.director.profile.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectorProfileReadRes {

    private String name;

    private String imgUrl;

    private String teamName;
}
