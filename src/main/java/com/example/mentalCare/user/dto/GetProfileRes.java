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
public class GetProfileRes {

    private Long id;

    private String userName;

    private Role role;

    private String teamName;

    private String teamCode;

    @Nullable
    private LocalDate nextMatchDate;

    @Nullable
    private Integer nextMatchDDay;

    @Nullable
    private Position position;

    @Builder(builderClassName = "directorBuilder", builderMethodName = "directorBuilder")
    public GetProfileRes (Long id, String userName, Role role, String teamName, String teamCode) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.teamName = teamName;
        this.teamCode = teamCode;
    }

    @Builder(builderClassName = "playerBuilder", builderMethodName = "playerBuilder")
    public GetProfileRes(Long id, String userName, Role role, String teamName, String teamCode, LocalDate nextMatchDate, Integer nextMatchDDay, Position position) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.teamName = teamName;
        this.teamCode = teamCode;
        this.nextMatchDate = nextMatchDate;
        this.nextMatchDDay = nextMatchDDay;
        this.position = position;
    }
}
