package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.Diagnose.Diagnose;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
public class AnswerQuestion {

    private String questionContext;
    private int weight;
    private Long diagnose_id;

}
