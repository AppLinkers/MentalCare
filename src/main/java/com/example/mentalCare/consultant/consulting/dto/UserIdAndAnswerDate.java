package com.example.mentalCare.consultant.consulting.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserIdAndAnswerDate {

    private Long userId;

    private LocalDateTime answerDate;

    public UserIdAndAnswerDate(Long userId, LocalDateTime answerDate) {
        this.userId = userId;
        this.answerDate = answerDate;
    }
}
