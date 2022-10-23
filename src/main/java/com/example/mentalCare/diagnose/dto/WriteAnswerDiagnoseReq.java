package com.example.mentalCare.diagnose.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WriteAnswerDiagnoseReq {

    private Long diagnoseId;

    private List<WriteAnswerDetailReq> writeAnswerDetailReqList;

    @Builder
    public WriteAnswerDiagnoseReq(Long diagnoseId, List<WriteAnswerDetailReq> writeAnswerDetailReqList) {
        this.diagnoseId = diagnoseId;
        this.writeAnswerDetailReqList = writeAnswerDetailReqList;
    }
}
