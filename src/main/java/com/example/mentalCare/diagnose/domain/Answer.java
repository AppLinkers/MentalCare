package com.example.mentalCare.diagnose.domain;

import com.example.mentalCare.user.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "answer")
    private List<AnswerDiagnose> answerDiagnoseList = new ArrayList<>();

    public void addAnswerDiagnose(AnswerDiagnose answerDiagnose) {
        this.answerDiagnoseList.add(answerDiagnose);
    }

    public AnswerDiagnose findAnswerDiagnoseByDiagnoseId(Long diagnoseId) {
        AnswerDiagnose result = new AnswerDiagnose();
        for (AnswerDiagnose answerDiagnose : this.answerDiagnoseList) {
            if (answerDiagnose.getDiagnose().getId().equals(diagnoseId)) {
                result = answerDiagnose;
            }
        }
        return result;
    }

    @Builder
    public Answer(Player player) {
        this.player = player;
    }
}
