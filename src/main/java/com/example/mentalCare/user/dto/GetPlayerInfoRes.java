package com.example.mentalCare.user.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPlayerInfoRes {

    private Long id;

    private String login_id;

    private String profileImg;

    private String name;

    private Role role;

    private String position;

    private Integer age;

}
