package com.example.mentalCare.user.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
public class GetProfileRes {

    private Long id;

    private String userName;

    private String imgUrl;

    private Role role;

    private String teamName;

    private String teamCode;

    @Nullable
    private LocalDate nextMatchDate;

    @Nullable
    private Integer nextMatchDDay;

    @Nullable
    private String position;

    @Builder(builderClassName = "directorBuilder", builderMethodName = "directorBuilder")
    public GetProfileRes (Long id, String userName, String imgUrl, Role role, String teamName, String teamCode) {
        this.id = id;
        this.userName = userName;
        this.imgUrl = imgUrl;
        this.role = role;
        this.teamName = teamName;
        this.teamCode = teamCode;
    }

    @Builder(builderClassName = "playerBuilder", builderMethodName = "playerBuilder")
    public GetProfileRes(Long id, String userName, String imgUrl, Role role, String teamName, String teamCode, LocalDate nextMatchDate, Integer nextMatchDDay, String position) {
        this.id = id;
        this.userName = userName;
        this.imgUrl = imgUrl;
        this.role = role;
        this.teamName = teamName;
        this.teamCode = teamCode;
        this.nextMatchDate = nextMatchDate;
        this.nextMatchDDay = nextMatchDDay;
        this.position = position;
    }
}
