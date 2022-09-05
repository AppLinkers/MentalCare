package com.example.mentalCare.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpReq {

    private String login_id;

    private String login_pw;

    private String name;

    private Integer age;
}
