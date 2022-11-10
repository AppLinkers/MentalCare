package com.example.mentalCare.user.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetTeamNotificationInfoRes {

    private Long id;

    private String directorName;

    private String title;

    private Integer viewCnt;

    private LocalDateTime createdAt;
}
