package com.example.mentalCare.user.domain;

import com.example.mentalCare.diagnose.domain.Answer;
import com.example.mentalCare.user.domain.type.Position;
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

    @Enumerated(value = EnumType.STRING)
    private Position position;

    @Builder.Default
    @OneToMany(mappedBy = "player")
    private List<Answer> answerList = new ArrayList<>();

    public void addAnswer(Answer answer) {
        this.answerList.add(answer);
    }
}
