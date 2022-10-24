package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.type.Position;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Setter
public class UpdatePlayerProfileReq {

    private String teamCode;

    private Position position;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate nextMatchDate;
}
