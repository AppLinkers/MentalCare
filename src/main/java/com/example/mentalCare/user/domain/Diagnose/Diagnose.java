package com.example.mentalCare.user.domain.Diagnose;

import com.example.mentalCare.test.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diagnose_id")
    private Long id;

    private String diagnoseTitle;

    @OneToMany(mappedBy = "diagnose", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Question> questionList;

    private String userId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Test.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "test_id")
    private Test test;

    public void newQuestion(Question question) {
        this.questionList.add(question);
    }


}
