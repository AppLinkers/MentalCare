package com.example.mentalCare.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetTeamNotificationDetailRes {

    private Long id;

    private String directorName;

    private String title;

    private String content;

    private Integer viewCnt;

    private LocalDateTime createdAt;
}
