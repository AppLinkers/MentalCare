package com.example.mentalCare.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteTeamNotificationReq {

    private String userLoginId;

    private String title;

    private String content;

}
