package com.example.mentalCare.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpUserReq {

    private String login_id;

    private String login_pw;

    private String teamCode;

    private String name;

    private Integer age;

    private Boolean privacyPolicy;

}
