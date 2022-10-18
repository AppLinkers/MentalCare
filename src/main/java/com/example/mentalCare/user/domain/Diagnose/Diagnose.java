package com.example.mentalCare.user.domain.Diagnose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Diagnose{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name= "diagnose_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnose_id;

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
