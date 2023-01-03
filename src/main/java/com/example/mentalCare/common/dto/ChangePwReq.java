package com.example.mentalCare.common.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePwReq {
    private String login_id;
    private String new_pw;
}
