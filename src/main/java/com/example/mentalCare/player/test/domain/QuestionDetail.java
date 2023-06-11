package com.example.mentalCare.player.test.domain;

import com.example.mentalCare.player.test.dto.QuestionDetailReadRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private Integer weight;

    private String optionValue;

    public QuestionDetailReadRes toQuestionDetailReadRes() {
        return QuestionDetailReadRes.builder()
                .id(this.id)
                .weight(weight)
                .optionValue(optionValue)
                .build();
    }
}
