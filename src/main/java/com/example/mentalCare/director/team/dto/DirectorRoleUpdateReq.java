package com.example.mentalCare.director.team.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DirectorRoleUpdateReq {

    private Long id;

    private Role role;

    @Builder
    public DirectorRoleUpdateReq(Long id, Role role){
        this.id = id;
        this.role = role;
    }

}
