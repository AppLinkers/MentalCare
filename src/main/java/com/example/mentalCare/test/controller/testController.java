package com.example.mentalCare.test.controller;

import com.example.mentalCare.user.domain.Diagnose.Question;
import com.example.mentalCare.user.dto.*;
import com.example.mentalCare.user.service.DiagnoseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("test")
@RequiredArgsConstructor
public class testController {

    private final DiagnoseService diagnoseService;


    /**
     * Test Page
     */
    @GetMapping("")
    public String testPage() {

        return "test/test";
    }

    @GetMapping("result/{id}")
    public String myResult(Model model, @PathVariable(value="id") Long id) {
        List<GetDiagnoseRes> diagnoseList = diagnoseService.getTestById(id).getDiagnoseList();

        int cnt =0;
        int sum=0;
        for(GetDiagnoseRes diagnoseRes : diagnoseList){
            for(Question q : diagnoseRes.getQuestionList()){
                cnt+=1;
                sum+=q.getWeight();
            }
        }
        model.addAttribute("diagnoseList", diagnoseList);
        model.addAttribute("date", diagnoseService.getTestById(id).getDate());
        model.addAttribute("totalAvg", sum/cnt);
        return "test/my_result";
    }

//    @GetMapping("/questionCalc/{diagnose}")
//    public void calcQuestion(Model model, @PathVariable(value="diagnose") Diagnose diagnose){
//        int result=0;
//        for(int i=0; i<diagnose.getQuestionList().size(); i++){
//            result+=diagnose.getQuestionList().get(i).getWeight();
//        }
//        model.addAttribute("average",result/diagnose.getQuestionList().size());
//    }

    @GetMapping("/progress")
    public String goToTest(GetDiagnoseRes getDiagnoseRes, Model model) throws IOException {
        BuildDiagnoseReq buildDiagnoseReq = new BuildDiagnoseReq();
        List<AnswerQuestion> answerQuestions = new ArrayList<>();
        List<GetDiagnoseRes> diagnoseListForm = diagnoseService.getTestById(0L).getDiagnoseList();

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

    @PostMapping("/submit")
    public String testSubmit( WriteTestReq writeTestReq,@ModelAttribute BuildDiagnoseReq req){
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        String todayFm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        writeTestReq.setUser_id(login_id);
        writeTestReq.setDate(todayFm);
        diagnoseService.writeTest(writeTestReq, req);
        return "redirect:/test/list";
    }

    /**
     * Test Result Page
     */
    @GetMapping("/list")
    public String testResultPage(Model model) {
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        List<GetTestRes> testList = diagnoseService.getAllTestByUserId(login_id);
        model.addAttribute("testList",testList);
        return "test/test_result";
    }
}
