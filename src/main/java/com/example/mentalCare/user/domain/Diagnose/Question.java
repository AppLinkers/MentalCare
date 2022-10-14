package com.example.mentalCare.user.domain.Diagnose;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name= "diagnose_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;

    private String questionContext;
    private int weight;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Diagnose.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "diagnose_id")
    private Diagnose diagnose;

}
