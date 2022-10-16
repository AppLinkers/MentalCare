package com.example.mentalCare.test.controller;

import com.example.mentalCare.user.domain.Diagnose.Diagnose;
import com.example.mentalCare.user.domain.Diagnose.Question;
import com.example.mentalCare.user.domain.Diagnose.Test;
import com.example.mentalCare.user.dto.*;
import com.example.mentalCare.user.service.DiagnoseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/myResult/{id}")
    public String myResult(Model model, @PathVariable(value="id") Long id) {
        model.addAttribute("diagnoseList", diagnoseService.getTestById(id).getDiagnoseList());
        model.addAttribute("date", diagnoseService.getTestById(id).getDate());
        System.out.println(diagnoseService.getTestById(id).getDiagnoseList().size());
        return "test/my_result";
    }

    @GetMapping("/testing")
    public String goToTest(GetDiagnoseRes getDiagnoseRes, Model model) throws IOException {
        BuildDiagnoseReq buildDiagnoseReq = new BuildDiagnoseReq();
        List<AnswerQuestion> answerQuestions = new ArrayList<>();
        List<Diagnose> diagnoseListForm = diagnoseService.getTestById(0L).getDiagnoseList();

        for(int i=0; i<diagnoseListForm.size(); i++) {
            for (int j = 0; j < diagnoseListForm.get(i).getQuestionList().size(); j++) {
                AnswerQuestion answerQuestion = new AnswerQuestion();
                answerQuestion.setQuestionContext(diagnoseListForm.get(i).getQuestionList().get(j).getQuestionContext());
                answerQuestion.setWeight(0);
                answerQuestions.add(answerQuestion);
            }
        }
        buildDiagnoseReq.setAnswerQuestions(answerQuestions);
        model.addAttribute("form", buildDiagnoseReq);
        model.addAttribute("diagnose", diagnoseListForm);
        return "test/testing";
    }

    @PostMapping("/addTest")
    public String testSubmit( WriteTestReq writeTestReq,@ModelAttribute BuildDiagnoseReq req){
        writeTestReq.setUser_id("asdf");
        writeTestReq.setDate("20.09.02");
        diagnoseService.writeTest(writeTestReq, req);
        //System.out.println(answerQuestion.getQuestionContext()+answerQuestion.getWeight()+"sipal");
       // diagnoseService.writeTest(writeTestReq, answerReq);
        return "test/my_result";
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
