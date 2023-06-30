package com.example.mentalCare.team.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TeamNotificationInfoRes {

    private Long id;

    private String directorName;

    private String title;

    private LocalDate createdAt;
}
