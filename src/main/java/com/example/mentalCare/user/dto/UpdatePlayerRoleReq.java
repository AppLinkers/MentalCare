package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePlayerRoleReq {

    private Long playerId;

    private Role role;
}
