package com.example.mentalCare.user.dto;


import com.example.mentalCare.user.domain.Diagnose.Diagnose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetQuestionRes {

    private Long id;
    private String questionContext;
    private int weight;
    private Diagnose diagnose;
}
