package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiagnoseResultPlayerInfoReadRes {

    private String name;

    private Integer age;

    private String position;

    private String teamName;

    private String nextMatchDDay;

    private String imgUrl;

}
