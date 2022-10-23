package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.type.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Builder
public class GetPlayerProfileRes {

    private Long id;

    private String userName;

    private String team;

    @Nullable
    private LocalDate nextMatchDate;

    private Integer nextMatchDDay;

    private Position position;

}
