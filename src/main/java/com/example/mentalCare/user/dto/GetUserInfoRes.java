package com.example.mentalCare.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetUserInfoRes {

    private Long id;

    private String login_id;
}
