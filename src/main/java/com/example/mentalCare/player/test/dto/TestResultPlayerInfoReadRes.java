package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestResultPlayerInfoReadRes {

    private String name;

    private Integer age;

    private String position;

    private String imgUrl;
}
