package com.example.mentalCare.diagnose.domain;

import javax.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDiagnose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "diagnose_id")
    private Diagnose diagnose;

    @OneToMany(mappedBy = "answerDiagnose")
    private List<AnswerDetail> answerDetailList = new ArrayList<>();

    public void addAnswerDetail(AnswerDetail answerDetail) {
        this.answerDetailList.add(answerDetail);
    }

    @Builder
    public AnswerDiagnose(Answer answer, Diagnose diagnose) {
        this.answer = answer;
        this.diagnose = diagnose;
    }
}
