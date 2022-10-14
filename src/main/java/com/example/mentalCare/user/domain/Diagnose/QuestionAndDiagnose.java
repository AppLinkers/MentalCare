package com.example.mentalCare.user.domain.Diagnose;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class QuestionAndDiagnose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="diagnose_id")
    private Diagnose diagnose;



    public QuestionAndDiagnose(Diagnose diagnose){
        this.diagnose = diagnose;
    }



}
