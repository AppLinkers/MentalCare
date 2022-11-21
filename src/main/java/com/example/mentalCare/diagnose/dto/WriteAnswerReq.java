package com.example.mentalCare.diagnose.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WriteAnswerReq {

    private List<WriteAnswerDiagnoseReq> writeAnswerDiagnoseReqList;

}
