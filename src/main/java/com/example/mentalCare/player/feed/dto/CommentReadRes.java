package com.example.mentalCare.player.feed.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CommentReadRes {

    private Long id;

    private String userLoginId;

    private String userName;

    private String userImgUrl;

    private String content;

    private LocalDate date;

}
