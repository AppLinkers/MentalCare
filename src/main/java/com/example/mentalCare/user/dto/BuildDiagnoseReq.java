package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.Diagnose.Question;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

@Data
public class BuildDiagnoseReq {

    private String diagnoseTitle;
    private String userId;
    private Long test_id;

    
}
