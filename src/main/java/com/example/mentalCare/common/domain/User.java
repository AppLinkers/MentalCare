package com.example.mentalCare.common.domain;

import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.common.domain.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login_id;

    private String login_pw;

    private String name;

    private String imgUrl;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
