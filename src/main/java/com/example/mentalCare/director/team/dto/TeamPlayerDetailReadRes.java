package com.example.mentalCare.director.team.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TeamPlayerDetailReadRes {

    private Long id;

    private String name;

    private String imgUrl;

    private Role role;

    private String position;

    private Integer age;

    private LocalDate answerDate;

}
