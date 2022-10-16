package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.type.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class GetPlayerRes {

    private Long id;

    private String userName;

    private String team;

    private String nextMatch;

    private Position position;

    public GetPlayerRes(){

    }




}
