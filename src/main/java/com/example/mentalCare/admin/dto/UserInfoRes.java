package com.example.mentalCare.admin.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoRes {

    private Long id;

    private String name;

    private Role role;

    private String imgUrl;

    private String teamName;

    private String teamCode;
}
