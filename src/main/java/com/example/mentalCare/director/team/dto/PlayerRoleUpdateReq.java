package com.example.mentalCare.director.team.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayerRoleUpdateReq {

    private Long id;

    private Role role;

    @Builder
    public PlayerRoleUpdateReq(Long id, Role role) {
        this.id = id;
        this.role = role;
    }
}
