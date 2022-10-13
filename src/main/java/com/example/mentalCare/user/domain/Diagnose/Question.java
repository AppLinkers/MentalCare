package com.example.mentalCare.user.domain.Diagnose;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long id;

    private String questionContext;
    private int weight;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Diagnose.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "diagnose_id")
    private Diagnose diagnose;


}
