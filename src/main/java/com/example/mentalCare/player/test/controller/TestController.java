package com.example.mentalCare.player.test.controller;

import com.example.mentalCare.player.test.dto.*;
import com.example.mentalCare.player.test.service.PlayerTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        playerTestService.submitTestAll(userLoginId, answerWriteReq);

        return "redirect:/player/test/result";
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
        model.addAttribute("answerWriteReq",answerWriteReq);

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
        model.addAttribute("answerDiagnoseWriteReq", answerDiagnoseWriteReq);

        return "z-renew/player/check";
    }

    /**
     * 유형 테스트 답변 제출 서비스
     */
    @PostMapping("/type")
    public String testTypeSubmit(AnswerWriteReq answerWriteReq, AnswerDiagnoseWriteReq answerDiagnoseWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DiagnoseAvgWriteReq> ad = new ArrayList<>();
        for(int i =1; i<9; i++){
            DiagnoseAvgWriteReq d1 = new DiagnoseAvgWriteReq();
            d1.setAvg(3);
            d1.setId(new Long(i));
            ad.add(d1);
        }
        answerDiagnoseWriteReq.setAnswerDiagnoseWriteReq(ad);
        playerTestService.submitTestType(userLoginId, answerWriteReq, answerDiagnoseWriteReq);

        return "redirect:/player/test/result";
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
        model.addAttribute("monthlyResult", playerTestService.getMonthlyResult(userLoginId, testResult.getDate()));

        return "z-renew/player/result";
    }
}