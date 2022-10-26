package com.example.mentalCare.diagnose.domain;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer answer;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "answer_diagnose_id")
    private AnswerDiagnose answerDiagnose;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}
