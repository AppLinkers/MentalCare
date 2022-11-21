package com.example.mentalCare.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Setter
public class UpdatePlayerProfileReq {

    private String teamCode;

    private String position;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate nextMatchDate;
}
