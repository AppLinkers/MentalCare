package com.example.mentalCare.user.dto;


import com.example.mentalCare.user.domain.Diagnose.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDiagnoseRes {
    private Long id;
    private String diagnoseTitle;
    private List<Question> questionList;
    private int diagnoseAvg;
    private String userId;
}
