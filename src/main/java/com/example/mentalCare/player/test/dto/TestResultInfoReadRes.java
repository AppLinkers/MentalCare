package com.example.mentalCare.player.test.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class TestResultInfoReadRes {

    private Long id;

    private LocalDate date;

    private List<String> diagnoseTypeList;
}
