package com.example.mentalCare.team.domain;

import com.example.mentalCare.common.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String code;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<User> userList = new ArrayList<>();

    @Builder
    public Team(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void addUser(User user) {
        userList.add(user);
        user.setTeam(this);
    }
}
