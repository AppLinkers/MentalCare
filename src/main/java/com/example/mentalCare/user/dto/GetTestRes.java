package com.example.mentalCare.user.dto;


import com.example.mentalCare.user.domain.Diagnose.Diagnose;
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
public class GetTestRes {

    private Long test_id;
    private String user_id;
    private String date;
    private List<Diagnose> diagnoseList;
}
