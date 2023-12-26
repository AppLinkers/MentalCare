package com.example.mentalCare.consultant.consulting.controller;

import com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithPlayerIdReadRes;
import com.example.mentalCare.consultant.consulting.service.ConsultingService;
import com.example.mentalCare.consultant.profile.service.ConsultantProfileService;
import com.example.mentalCare.player.test.dto.MonthlyResultReadRes;
import com.example.mentalCare.player.test.dto.TestDiagnoseResultReadRes;
import com.example.mentalCare.player.test.dto.TestResultReadRes;
import com.example.mentalCare.player.test.service.PlayerTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/consultant")
@RequiredArgsConstructor
public class ConsultantConsultingController {

    private final ConsultingService consultingService;
    private final ConsultantProfileService consultantProfileService;
    private final PlayerTestService playerTestService;

    /**
     * 담당 팀 선수들 리스트 및 순위 화면 호출
     */
    @GetMapping("/team/player")
    public String teamPlayerListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("totalDiagnoseResultReadResList", consultingService.getTotalDiagnoseResultByTeam(userLoginId));
        model.addAttribute("typeDiagnoseResultReadResList", consultingService.getTypeDiagnoseResultByTeam(userLoginId));

        return "z-renew/consultant/team_player_list";
    }

    /**
     * 담당 개인 선수들 리스트 및 순위 화면 호출
     */
    @GetMapping("/individual/player")
    public String individualPlayerListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("totalDiagnoseResultReadResList", consultingService.getTotalDiagnoseResultByIndividual(userLoginId));
        model.addAttribute("typeDiagnoseResultReadResList", consultingService.getTypeDiagnoseResultByIndividual(userLoginId));

        return "z-renew/consultant/team_player_list";
    }

    /**
     * 특정 선수의 검사 결과 리스트 화면 호출
     */
    @GetMapping("/test/result/player/{id}")
    public String playerTestResultListPage(Model model, @PathVariable Long id) {

        model.addAttribute("player", consultingService.getPlayerInfoOfTestResult(id));
        model.addAttribute("testResultInfoList", consultingService.getAllTestResultByPlayerId(id));

        return "z-renew/consultant/player_test_result_list";
    }

    /**
     * 테스트 결과 상세 페이지
     */
    @GetMapping("/test/result/{id}")
    public String testResultPage(Model model, @PathVariable Long id) {

        String userLoginId = consultingService.getPlayerUserLoginIdByTestId(id);

        model.addAttribute("player", playerTestService.getPlayerInfoOfTestResult(userLoginId));
        TestResultReadRes testResult = playerTestService.getTestResult(id);
        model.addAttribute("testResult", testResult);
        model.addAttribute("positionResult", playerTestService.getPositionResult(userLoginId));
        model.addAttribute("ageResult", playerTestService.getAgeResult(userLoginId));
        List<MonthlyResultReadRes> monthlyResult = playerTestService.getMonthlyResult(userLoginId, testResult.getDate());
        model.addAttribute("monthlyResult", monthlyResult);

        List<List> monthlyResultAvgList = new ArrayList<>();
        List<List> monthlyResultAvg = new ArrayList<>();
        List<Double> monthlyTotalAvg = new ArrayList<>();

        for (MonthlyResultReadRes m : monthlyResult) {
            List<Double> monthlyAvg = new ArrayList<>();
            if (m.getDiagnoseResultList().size() == 0) {
                for (int i = 0; i < 9; i++) {
                    monthlyAvg.add(0.0);
                }
            } else {
                for (TestDiagnoseResultReadRes tdr : m.getDiagnoseResultList()) {
                    monthlyAvg.add(tdr.getAvg());
                }
                Collections.reverse(monthlyAvg);
            }
            monthlyResultAvgList.add(monthlyAvg);
        }


        for (int j = 0; j < 9; j++) {
            List<Double> monthResultAvg = new ArrayList<>();
            for (int i = 0; i < monthlyResultAvgList.size(); i++) {
                monthResultAvg.add((Double) monthlyResultAvgList.get(i).get(j));
            }
            monthlyResultAvg.add(monthResultAvg);
        }

        for (int i = 0; i < monthlyResultAvgList.size(); i++) {
            Double sum = getSum(monthlyResultAvgList.get(i));
            monthlyTotalAvg.add(Math.round((sum * 100) / 9) / 100.0);
        }

        model.addAttribute("monthlyTotalAvg", monthlyTotalAvg);
        model.addAttribute("monthlyResultAvg", monthlyResultAvg);


        return "z-renew/consultant/player_test_result";
    }


    public static Double getSum(List<Double> nums) {
        Double sum = 0.0;
        for (Double i : nums) {
            sum += i;
        }
        return sum;
    }

    /**
     * 담당 팀 선수들의 평균 검사 결과 조회 화면 호출
     */
    @GetMapping("/team/test/result")
    public String teamTestResultPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        double totalAvg = 0;
        int totalPlayer = consultingService.getTotalDiagnoseResultByTeam(userLoginId).size();

        for(DiagnoseResultWithPlayerIdReadRes i : consultingService.getTotalDiagnoseResultByTeam(userLoginId)){
            totalAvg+=i.getAvg();
        }

        totalAvg = totalAvg / totalPlayer;

        model.addAttribute("testDiagnoseResultList", consultingService.getTeamPlayerDiagnoseResult(userLoginId));
        model.addAttribute("profile", consultantProfileService.getProfileRead(userLoginId).getTeamName());
        model.addAttribute("totalPlayer", totalPlayer);
        model.addAttribute("totalAvg", Math.round(totalAvg*100)/100.0);
        model.addAttribute("monthlyTotalAvgList", consultingService.getTeamPlayerMonthlyTotalAvg(userLoginId));
        model.addAttribute("monthlyTypeAvgList", consultingService.getTeamPlayerMonthlyTypeAvg(userLoginId));

        return "z-renew/consultant/test_result";
    }

    /**
     * 담당 개인 선수들의 평균 검사 결과 조회 화면 호출
     */
    @GetMapping("/individual/test/result")
    public String individualTestResultPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        double totalAvg = 0;
        int totalPlayer = consultingService.getTotalDiagnoseResultByIndividual(userLoginId).size();

        for(DiagnoseResultWithPlayerIdReadRes i : consultingService.getTotalDiagnoseResultByIndividual(userLoginId)){
            totalAvg+=i.getAvg();
        }

        totalAvg = totalAvg / totalPlayer;

        model.addAttribute("profile", "개인 선수");
        model.addAttribute("totalPlayer", totalPlayer);
        model.addAttribute("totalAvg", Math.round(totalAvg*100)/100.0);

        model.addAttribute("testDiagnoseResultList", consultingService.getIndividualPlayerDiagnoseResult(userLoginId));
        model.addAttribute("monthlyTotalAvgList", consultingService.getIndividualPlayerMonthlyTotalAvg(userLoginId));
        model.addAttribute("monthlyTypeAvgList", consultingService.getIndividualPlayerMonthlyTypeAvg(userLoginId));

        return "z-renew/consultant/test_result";
    }

    @ResponseBody
    @PostMapping("request_test")
    public void requestTest() {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        consultingService.testRequest(userLoginId);
    }

    @ResponseBody
    @PostMapping("request_test/{id}")
    public void requestTestByPlayerId(@PathVariable Long id) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        consultingService.testRequestByPlayerId(userLoginId, id);
    }
}
