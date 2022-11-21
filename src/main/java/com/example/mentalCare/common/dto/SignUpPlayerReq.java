package com.example.mentalCare.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpPlayerReq extends SignUpUserReq {

    private String position;

}
