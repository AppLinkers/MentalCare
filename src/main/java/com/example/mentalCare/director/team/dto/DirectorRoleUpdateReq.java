package com.example.mentalCare.director.team.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectorRoleUpdateReq {

    private Long id;

    private Role role;

}
