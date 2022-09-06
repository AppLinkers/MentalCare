package com.example.mentalCare.user.domain;

import javax.persistence.*;

import com.example.mentalCare.user.domain.type.Role;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;
}
