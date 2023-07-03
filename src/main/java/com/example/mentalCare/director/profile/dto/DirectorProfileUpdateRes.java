package com.example.mentalCare.director.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectorProfileUpdateRes {

    private String name;

    private String imgUrl;

    private String teamCode;


    public DirectorProfileUpdateReq toDirectorProfileUpdateReq() {
        return DirectorProfileUpdateReq.builder()
                .teamCode(teamCode)
                .build();
    }
}
