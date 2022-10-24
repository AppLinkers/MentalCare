package com.example.mentalCare.diagnose.controller;

import com.example.mentalCare.diagnose.domain.Diagnose;
import com.example.mentalCare.diagnose.dto.*;
import com.example.mentalCare.diagnose.service.DiagnoseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/progress")
    public String goToTest(Model model) {
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<GetDiagnoseRes> diagnoseList = diagnoseService.getDiagnoseResList();

        model.addAttribute("diagnoseList", diagnoseList);

        WriteAnswerReq writeAnswerReq = new WriteAnswerReq();

        writeAnswerReq.setUserLoginId(login_id);

        List<WriteAnswerDiagnoseReq> writeAnswerDiagnoseReqList = new ArrayList<>();
        for (GetDiagnoseRes diagnoseRes : diagnoseList) {
            List<WriteAnswerDetailReq> writeAnswerDetailReqList = new ArrayList<>();
            for (GetQuestionRes questionRes : diagnoseRes.getQuestionResList()) {
                writeAnswerDetailReqList.add(new WriteAnswerDetailReq(questionRes.getQuestionId()));
            }

            WriteAnswerDiagnoseReq writeAnswerDiagnoseReq = WriteAnswerDiagnoseReq.builder()
                    .diagnoseId(diagnoseRes.getDiagnoseId())
                    .writeAnswerDetailReqList(writeAnswerDetailReqList)
                    .build();
            writeAnswerDiagnoseReqList.add(writeAnswerDiagnoseReq);
        }

        writeAnswerReq.setWriteAnswerDiagnoseReqList(writeAnswerDiagnoseReqList);
        model.addAttribute("writeAnswerReq", writeAnswerReq);
        return "test/testing";
    }

    @PostMapping("/submit")
    public String testSubmit(WriteAnswerReq writeAnswerReq){

        diagnoseService.submitAnswer(writeAnswerReq);
        return "redirect:/test/list";
    }

    @PostMapping("/typeSubmit")
    public String typeSubmit(WriteAnswerReq writeAnswerReq){
        ;
        System.out.println(writeAnswerReq.getWriteAnswerDiagnoseReqList().get(0).getDiagnoseId());
        return "redirect:/test/list";
    }

    /**
     * Test Result Page
     */
    @GetMapping("/list")
    public String testResultPage(Model model) {
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<GetAnswerInfoRes> answerInfoResList = diagnoseService.getAllAnswerByUserLoginId(login_id);

        model.addAttribute("answerInfoList",answerInfoResList);
        return "test/test_result";
    }

    /**
     * 진단 결과 호출 페이지
     */
    @GetMapping("result/{id}")
    public String myResult(Model model, @PathVariable(value="id") Long id) {
        GetAnswerRes answer = diagnoseService.getAnswerByAnswerId(id);

        model.addAttribute("answer", answer);
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


    /**
     * Teest Type Page
     */
    @GetMapping("/type")
    public  String testTypePage(Model model){
        List<GetDiagnoseRes> diagnoseList = diagnoseService.getDiagnoseResList();
        model.addAttribute("diagnoseList",diagnoseList);
        return "test/test_type";
    }

    @GetMapping("/typetest/{id}")
    public String typeTestingPage(Model model, @PathVariable(value="id") Long id){
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        WriteAnswerReq writeAnswerReq = new WriteAnswerReq();
        writeAnswerReq.setUserLoginId(login_id);

        List<WriteAnswerDiagnoseReq> writeAnswerDiagnoseReqList = new ArrayList<>();


        GetDiagnoseRes diagnoseRes = diagnoseService.diagnosebyId(id);
        model.addAttribute("diagnose", diagnoseRes);

        List<WriteAnswerDetailReq> writeAnswerDetailReqList = new ArrayList<>();

        for (GetQuestionRes questionRes : diagnoseRes.getQuestionResList()) {
            writeAnswerDetailReqList.add(new WriteAnswerDetailReq(questionRes.getQuestionId()));
        }


        WriteAnswerDiagnoseReq writeAnswerDiagnoseReq = WriteAnswerDiagnoseReq.builder()
                .diagnoseId(diagnoseRes.getDiagnoseId())
                .writeAnswerDetailReqList(writeAnswerDetailReqList)
                .build();

        writeAnswerDiagnoseReqList.add(writeAnswerDiagnoseReq);


        writeAnswerReq.setWriteAnswerDiagnoseReqList(writeAnswerDiagnoseReqList);
        model.addAttribute("writeAnswerReq", writeAnswerReq);

        return "test/type_testing";
    }
}
