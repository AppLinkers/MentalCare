package com.example.mentalCare.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpUserReq {

    private String login_id;

    private String login_pw;

    private String teamCode;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Boolean privacyPolicy;

}
