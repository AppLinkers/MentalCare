package com.example.mentalCare.test.controller;

import com.example.mentalCare.user.domain.Diagnose.Question;
import com.example.mentalCare.user.domain.Diagnose.Test;
import com.example.mentalCare.user.dto.BuildDiagnoseReq;
import com.example.mentalCare.user.dto.GetDiagnoseRes;
import com.example.mentalCare.user.dto.GetTestRes;
import com.example.mentalCare.user.service.DiagnoseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class testController {

    private final DiagnoseService diagnoseService;


    /**
     * Test Page
     */
    @GetMapping("/test")
    public String testPage() {
        return "test/test";
    }

    @GetMapping("/myResult")
    public String myResult(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GetDiagnoseRes> diagnoseList = diagnoseService.getAllDiagnose(pageable);
        List<GetDiagnoseRes> dList = diagnoseList.getContent();
        model.addAttribute("diagnoseList", diagnoseList);
        return "test/my_result";
    }

    @GetMapping("/testing")
    public String goToTest(GetDiagnoseRes getDiagnoseRes) throws IOException {
        return "test/testing";
    }

    /**
     * Test Result Page
     */
    @GetMapping("/testList")
    public String testResultPage(Model model) {
        List<GetTestRes> testList = diagnoseService.getAllTestByUserId("asdf");
        model.addAttribute("testList",testList);
        return "test/test_result";
    }
}
