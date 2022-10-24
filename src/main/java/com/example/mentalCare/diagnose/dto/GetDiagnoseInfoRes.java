package com.example.mentalCare.diagnose.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetDiagnoseInfoRes {

    private Long diagnoseId;

    private String diagnoseTitle;

}
