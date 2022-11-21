package com.example.mentalCare.user.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetUserInfoRes {

    private Long id;

    private String login_id;

    private String name;

    private Role role;
}
