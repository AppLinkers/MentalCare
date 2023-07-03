package com.example.mentalCare.director.team.dto;

import com.example.mentalCare.common.domain.type.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class TeamPlayerDetailReadRes {

    private Long id;

    private String name;

    private String imgUrl;

    private Role role;

    private String position;

    private Integer age;

    @Nullable
    private LocalDate answerDate;

    private Double avg;

    public PlayerInfoUpdateReq toPlayerInfoUpdateReq() {
        return PlayerInfoUpdateReq.builder()
                .id(this.id)
                .role(this.role)
                .build();
    }

}
