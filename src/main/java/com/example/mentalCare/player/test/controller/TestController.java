package com.example.mentalCare.player.test.controller;

import com.example.mentalCare.player.test.dto.*;
import com.example.mentalCare.player.test.service.PlayerTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/player/test")
@RequiredArgsConstructor
public class TestController {

    private final PlayerTestService playerTestService;

    /**
     * 통합 테스트 페이지
     */
    @GetMapping("/all")
    public String testAllPage(Model model) {

        List<DiagnoseReadRes> diagnoseList = playerTestService.getTestAllDiagnoseRead();

        model.addAttribute("diagnoseList", diagnoseList);

        AnswerWriteReq answerWriteReq = new AnswerWriteReq();
        List<DiagnoseWriteReq> diagnoseWriteReqList = new ArrayList<>();
        for (DiagnoseReadRes diagnoseReadRes : diagnoseList) {
            DiagnoseWriteReq diagnoseWriteReq = diagnoseReadRes.diagnoseReadResToDiagnoseWriteReq();
            diagnoseWriteReqList.add(diagnoseWriteReq);
        }
        answerWriteReq.setAnswerWriteReq(diagnoseWriteReqList);
        model.addAttribute("answerWriteReq",answerWriteReq);

        return "z-renew/player/test_all";
    }

    /**
     * 통합 테스트 답변 제출 서비스
     */
    @PostMapping("/all")
    public String testAllSubmit(AnswerWriteReq answerWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        long id = playerTestService.submitTestAll(userLoginId, answerWriteReq);

        return "redirect:/player/test/result/"+id;
    }

    /**
     * 유형 테스트 - 유형 선택 페이지
     */
    @GetMapping("/type_select")
    public String testTypeSelectPage(Model model) {

        model.addAttribute("diagnoseInfoList", playerTestService.getAllDiagnoseInfoRead());

        return "z-renew/player/select_type";
    }

    /**
     * 유형 테스트 페이지
     */
    @GetMapping("/type")
    public String testMultiTypePate(Model model, @RequestParam(value = "ids") List<Long> ids) {

        List<DiagnoseReadRes> diagnoseList = playerTestService.getMultiTestDiagnoseRead(ids);

        model.addAttribute("diagnoseList", diagnoseList);

        AnswerWriteReq answerWriteReq = new AnswerWriteReq();
        List<DiagnoseWriteReq> diagnoseWriteReqList = new ArrayList<>();
        for (DiagnoseReadRes diagnoseReadRes : diagnoseList) {
            DiagnoseWriteReq diagnoseWriteReq = diagnoseReadRes.diagnoseReadResToDiagnoseWriteReq();
            diagnoseWriteReqList.add(diagnoseWriteReq);
        }
        answerWriteReq.setAnswerWriteReq(diagnoseWriteReqList);

        List<DiagnoseInfoReadRes> diagnoseInfoList = playerTestService.getDiagnoseInfoReadExceptByIds(ids);

        model.addAttribute("diagnoseInfoList", diagnoseInfoList);

        AnswerDiagnoseWriteReq answerDiagnoseWriteReq = new AnswerDiagnoseWriteReq();
        List<DiagnoseAvgWriteReq> diagnoseAvgWriteReqList = new ArrayList<>();
        for (DiagnoseInfoReadRes diagnoseInfoReadRes : diagnoseInfoList) {
            DiagnoseAvgWriteReq diagnoseAvgWriteReq = DiagnoseAvgWriteReq.builder()
                    .id(diagnoseInfoReadRes.getId()).build();

            diagnoseAvgWriteReqList.add(diagnoseAvgWriteReq);
        }

        answerDiagnoseWriteReq.setAnswerDiagnoseWriteReq(diagnoseAvgWriteReqList);

        TypeTestReq typeTestReq = TypeTestReq.builder()
                .answerWriteReq(answerWriteReq)
                .answerDiagnoseWriteReq(answerDiagnoseWriteReq)
                .build();

        model.addAttribute("typeTestReq", typeTestReq);

        return "z-renew/player/test_type";
    }

    /**
     * 유형 테스트 답변 제출 서비스
     */
    @PostMapping("/type")
    public String testTypeSubmit(TypeTestReq typeTestReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        AnswerWriteReq answerWriteReq = typeTestReq.getAnswerWriteReq();
        AnswerDiagnoseWriteReq answerDiagnoseWriteReq = typeTestReq.getAnswerDiagnoseWriteReq();

        long id = playerTestService.submitTestType(userLoginId, answerWriteReq, answerDiagnoseWriteReq);

        return "redirect:/player/test/result/"+id;
    }

    /**
     * 테스트 결과 목록 페이지
     */
    @GetMapping("/result")
    public String testResultListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("testResultInfoList", playerTestService.getAllTestResultByUserLoginId(userLoginId));
        return "z-renew/player/result_list";
    }



    /**
     * 테스트 결과 상세 페이지
     */
    @GetMapping("/result/{id}")
    public String testResultPage(Model model, @PathVariable Long id) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("player", playerTestService.getPlayerInfoOfTestResult(userLoginId));
        TestResultReadRes testResult = playerTestService.getTestResult(id);
        model.addAttribute("testResult", testResult);
        model.addAttribute("positionResult", playerTestService.getPositionResult(userLoginId));
        model.addAttribute("ageResult", playerTestService.getAgeResult(userLoginId));
        List<MonthlyResultReadRes> monthlyResult = playerTestService.getMonthlyResult(userLoginId, testResult.getDate());
        model.addAttribute("monthlyResult", monthlyResult);

        List<List> monthlyResultAvgList = new ArrayList<List>();
        List<List> monthlyResultAvg = new ArrayList<List>();
        List<Double> monthlyTotalAvg = new ArrayList<Double>();


        for (MonthlyResultReadRes m : monthlyResult) {
            List<Double> monthlyAvg = new ArrayList<Double>();
            if(m.getDiagnoseResultList().size() == 0){
                for(int i =0; i<9; i++){
                    monthlyAvg.add(0.0);
                }
            }
            else {
                for (TestDiagnoseResultReadRes tdr : m.getDiagnoseResultList()) {
                    monthlyAvg.add(tdr.getAvg());
                }
                Collections.reverse(monthlyAvg);
            }
            monthlyResultAvgList.add(monthlyAvg);
        }




        for(int j=0; j<9; j++){
            List<Double> monthResultAvg = new ArrayList<Double>();
            for(int i = 0; i<monthlyResultAvgList.size(); i++){
                monthResultAvg.add((Double) monthlyResultAvgList.get(i).get(j));
            }
            monthlyResultAvg.add(monthResultAvg);
        }

        for(int i = 0; i<monthlyResultAvgList.size(); i++){
            Double sum = getSum(monthlyResultAvgList.get(i));
            monthlyTotalAvg.add( Math.round((sum*100)/9)/100.0);
        }

        model.addAttribute("monthlyTotalAvg", monthlyTotalAvg);
        model.addAttribute("monthlyResultAvg", monthlyResultAvg);


        return "z-renew/player/result";
    }


    public static Double getSum(List<Double> nums) {
        Double sum = 0.0;
        for (Double i: nums) {
            sum += i;
        }
        return sum;
    }
}