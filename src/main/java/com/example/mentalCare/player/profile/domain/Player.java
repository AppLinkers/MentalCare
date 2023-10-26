package com.example.mentalCare.player.profile.domain;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.consultant.profile.domain.Consultant;
import com.example.mentalCare.player.test.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Nullable
    private LocalDate nextMatch;

    private String position;

    @Builder.Default
    @OneToMany(mappedBy = "player")
    private List<Answer> answerList = new ArrayList<>();

    public void addAnswer(Answer answer) {
        this.answerList.add(answer);
    }

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;
}
