package com.example.mentalCare.director.team.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DirectorRoleUpdateReq {

    private Long id;

    private Role role;

}
