package com.example.mentalCare.director.team.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerInfoUpdateReq {

    private Long id;

    private String position;

    private Role role;

}
