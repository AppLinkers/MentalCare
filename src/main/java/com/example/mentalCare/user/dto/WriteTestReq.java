package com.example.mentalCare.user.dto;


import com.example.mentalCare.user.domain.Diagnose.Diagnose;
import lombok.Data;

import java.util.List;

@Data
public class WriteTestReq {

    private String user_id;
    private String date;
    private List<Diagnose> diagnoseList;
}
