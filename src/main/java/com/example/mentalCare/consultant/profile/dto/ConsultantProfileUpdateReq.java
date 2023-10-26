package com.example.mentalCare.consultant.profile.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ConsultantProfileUpdateReq {

    private String teamCode;

    private MultipartFile imgFile;

    @Builder
    public ConsultantProfileUpdateReq(String teamCode) {
        this.teamCode = teamCode;
    }
}

