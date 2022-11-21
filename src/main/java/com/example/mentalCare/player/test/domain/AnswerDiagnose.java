package com.example.mentalCare.player.test.domain;

import javax.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "answerDiagnose")
    private List<AnswerDetail> answerDetailList = new ArrayList<>();

    public void addAnswerDetailList(List<AnswerDetail> answerDetailListReq) {
        this.answerDetailList.addAll(answerDetailListReq);
    }

    @Builder
    public AnswerDiagnose(Answer answer, Diagnose diagnose) {
        this.answer = answer;
        this.diagnose = diagnose;
    }

    public Double getAvg() {
        Double avg = 0.0;

        for (AnswerDetail answerDetail : this.answerDetailList) {
            avg += answerDetail.getAnswer();
        }

        return avg / this.answerDetailList.size();
    }
}
