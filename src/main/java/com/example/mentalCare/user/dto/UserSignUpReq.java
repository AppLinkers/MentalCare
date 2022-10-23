package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.type.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpReq {

    private String login_id;

    private String login_pw;

    private String team;

    private String name;

    private Integer age;

    public User userSignUpReqToUser(UserSignUpReq request, Role role) {
        return User.builder()
                .login_id(request.getLogin_id())
                .login_pw(request.getLogin_pw())
                .name(request.getName())
                .team(request.getTeam())
                .age(request.getAge())
                .role(role)
                .build();
    }
}
