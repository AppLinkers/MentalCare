package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.type.Position;
import com.example.mentalCare.user.domain.type.Role;
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

    private Role role;

    private String teamName;

    @Nullable
    private LocalDate nextMatchDate;

    private Integer nextMatchDDay;

    private Position position;

}
