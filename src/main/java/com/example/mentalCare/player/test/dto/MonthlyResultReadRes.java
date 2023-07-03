package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MonthlyResultReadRes {

    private String yearMonth;

    private List<TestDiagnoseResultReadRes> diagnoseResultList;
}
