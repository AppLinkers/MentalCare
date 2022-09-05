package com.example.mentalCare.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ReadUserInfoRes {

    private Long id;

    private String login_id;
}
