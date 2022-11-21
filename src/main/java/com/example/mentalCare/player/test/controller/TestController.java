package com.example.mentalCare.player.test.controller;

import com.example.mentalCare.diagnose.dto.WriteAnswerReq;
import com.example.mentalCare.player.test.dto.AnswerWriteReq;
import com.example.mentalCare.player.test.dto.DiagnoseReadRes;
import com.example.mentalCare.player.test.dto.DiagnoseWriteReq;
import com.example.mentalCare.player.test.dto.TypeDiagnoseReadRes;
import com.example.mentalCare.player.test.service.PlayerTestService;
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

@Controller
@RequestMapping("/player/test")
@RequiredArgsConstructor
public class TestController {

    private final PlayerTestService playerTestService;

    /**
     * Test Page
     */
    @GetMapping("")
    public String testPage() {
        return "player/test";
    }

    /**
     * Test All Page
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
     * Test All Submit Service
     */
    @PostMapping("/all")
    public String testAllSubmit(AnswerWriteReq answerWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        playerTestService.submitTestAll(userLoginId, answerWriteReq.getAnswerWriteReq());

        return "redirect:/player/test/result";
    }

    /**
     * Test Type Select Page
     */
    @GetMapping("/type_select")
    public String testTypeSelectPage(Model model) {

        model.addAttribute("diagnoseInfoList", playerTestService.getAllDiagnoseInfoRead());

        return "player/test_type_select";
    }

    /**
     * Test Type Page
     */
    @GetMapping("/type/{id}")
    public String testTypePage(Model model, @PathVariable Long id) {

        TypeDiagnoseReadRes typeDiagnoseReadRes = playerTestService.getTestTypeDiagnoseRead(id);
        model.addAttribute("diagnose", typeDiagnoseReadRes);

        model.addAttribute("diagnoseWriteReq", typeDiagnoseReadRes.typeDiagnoseReadResToDiagnoseWriteReq());

        return "player/test_type";
    }

    /**
     * Test Type Submit Service
     */
    @PostMapping("/type")
    public String testTypeSubmit(DiagnoseWriteReq diagnoseWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(diagnoseWriteReq.getId()+"::test ::" +diagnoseWriteReq.getQuestionWriteReqList().get(0).getId()+":: test");
        playerTestService.submitTestType(userLoginId, diagnoseWriteReq);

        return "redirect:/player/test/result";
    }

    /**
     * Test Result List Page
     */
    @GetMapping("/result")
    public String testResultListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("testResultInfoList", playerTestService.getAllTestResultByUserLoginId(userLoginId));
        return "player/test_result_list";
    }

    /**
     * Test Result Page
     */
    @GetMapping("/result/{id}")
    public String testResultPage(Model model, @PathVariable Long id) {
        model.addAttribute("player", playerTestService.getPlayerInfoOfTestResult(id));
        model.addAttribute("testResult", playerTestService.getTestResult(id));

        return "player/test_result";
    }
}
