package com.example.mentalCare.consultant.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsultantProfileReadRes {

    private String name;

    private String imgUrl;

    private String teamName;
}
