package com.example.mentalCare.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginReq {

    private String login_id;

    private String login_pw;
}
