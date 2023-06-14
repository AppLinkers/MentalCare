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
     * 테스트 선택 페이지
     */
    @GetMapping("")
    public String testPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("isLastExist",playerTestService.isLastAnswerExist(userLoginId));
        return "player/test";
    }

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

        return "player/test_all";
    }

    /**
     * 통합 테스트 답변 제출 서비스
     */
    @PostMapping("/all")
    public String testAllSubmit(AnswerWriteReq answerWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        playerTestService.submitTestAll(userLoginId, answerWriteReq.getAnswerWriteReq());

        return "redirect:/player/test/result";
    }

    /**
     * (다수 선택) 유형 테스트 페이지
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

        return "test";
    }

    /**
     * 유형 테스트 - 유형 선택 페이지
     */
    @GetMapping("/type_select")
    public String testTypeSelectPage(Model model) {

        model.addAttribute("diagnoseInfoList", playerTestService.getAllDiagnoseInfoRead());

        return "player/test_type_select";
    }

    /**
     * 유형 테스트 페이지
     */
    @GetMapping("/type/{id}")
    public String testTypePage(Model model, @PathVariable Long id) {

        TypeDiagnoseReadRes typeDiagnoseReadRes = playerTestService.getTestTypeDiagnoseRead(id);
        model.addAttribute("diagnose", typeDiagnoseReadRes);

        model.addAttribute("diagnoseWriteReq", typeDiagnoseReadRes.typeDiagnoseReadResToDiagnoseWriteReq());

        return "player/test_type";
    }

    /**
     * 유형 테스트 답변 제출 서비스
     */
    @PostMapping("/type")
    public String testTypeSubmit(DiagnoseWriteReq diagnoseWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        playerTestService.submitTestType(userLoginId, diagnoseWriteReq);

        return "redirect:/player/test/result";
    }

    /**
     * 테스트 결과 목록 페이지
     */
    @GetMapping("/result")
    public String testResultListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("testResultInfoList", playerTestService.getAllTestResultByUserLoginId(userLoginId));
        return "player/test_result_list";
    }

    /**
     * 테스트 결과 상세 페이지
     */
    @GetMapping("/result/{id}")
    public String testResultPage(Model model, @PathVariable Long id) {
        model.addAttribute("player", playerTestService.getPlayerInfoOfTestResult(id));
        model.addAttribute("testResult", playerTestService.getTestResult(id));
        return "player/test_result";
    }
}
