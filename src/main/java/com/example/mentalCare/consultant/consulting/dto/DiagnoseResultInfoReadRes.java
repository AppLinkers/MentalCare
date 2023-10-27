package com.example.mentalCare.consultant.consulting.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DiagnoseResultInfoReadRes {

    private Long id;

    private LocalDate date;

}
