package com.example.mentalCare.player.consulting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsultantInfoReadRes {

    private Long id;

    private String imgUrl;

    private String name;

    private Boolean hasRequest;
}
