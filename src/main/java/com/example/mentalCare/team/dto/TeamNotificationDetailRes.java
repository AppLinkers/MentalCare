package com.example.mentalCare.team.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class TeamNotificationDetailRes {

    private Long id;

    private String directorName;

    private String directorImgUrl;

    private String title;

    private String content;

    private Integer viewCnt;

    private LocalDate createdAt;
}
