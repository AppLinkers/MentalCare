package com.example.mentalCare.player.profile.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class PlayerProfileUpdateReq {

    private String teamCode;

    private String position;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate nextMatchDate;

    private MultipartFile imgFile;
}
