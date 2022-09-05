package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.type.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerSignUpReq extends UserSignUpReq{

    private Position position;
}
