package com.example.mentalCare.team.domain;

import javax.persistence.*;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.player.test.domain.AnswerDetail;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

}
