package com.example.mentalCare.director.team.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamConsultantInfoReadRes {

    private Long id;

    private String imgUrl;

    private String name;

    private String teamName;

    private Role role;

    public ConsultantRoleUpdateReq toConsultantRoleUpdateReq() {
        return ConsultantRoleUpdateReq.builder()
                .id(this.id)
                .role(this.role)
                .build();
    }

}
