package com.example.mentalCare.consultant.profile.dto;

import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateReq;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsultantProfileUpdateRes {

    private String name;

    private String imgUrl;

    private String teamCode;


    public ConsultantProfileUpdateReq toConsultantProfileUpdateReq() {
        return ConsultantProfileUpdateReq.builder()
                .teamCode(teamCode)
                .build();
    }
}
