package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.Diagnose.Diagnose;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.lang.Nullable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
public class AnswerQuestion {
    private String questionContext;
    private int weight;

}
