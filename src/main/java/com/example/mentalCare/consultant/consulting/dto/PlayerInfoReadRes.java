package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlayerInfoReadRes {

    private Long id;

    private String imgUrl;

    private String name;
}
