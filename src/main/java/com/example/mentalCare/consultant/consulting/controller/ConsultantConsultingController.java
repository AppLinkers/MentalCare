package com.example.mentalCare.consultant.consulting.controller;

import com.example.mentalCare.consultant.consulting.service.ConsultingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consultant")
@RequiredArgsConstructor
public class ConsultantConsultingController {

    private final ConsultingService consultingService;

    /**
     * 담당 팀 선수들 리스트 및 순위 화면 호출
     */
    @GetMapping("/team/player")
    public String teamPlayerListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("totalDiagnoseResultReadResList", consultingService.getTotalDiagnoseResultByTeam(userLoginId));
        model.addAttribute("typeDiagnoseResultReadResList", consultingService.getTypeDiagnoseResultByTeam(userLoginId));

        return "consultant/team_player_list";
    }

    /**
     * 담당 개인 선수들 리스트 및 순위 화면 호출
     */
    @GetMapping("/individual/player")
    public String individualPlayerListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("totalDiagnoseResultReadResList", consultingService.getTotalDiagnoseResultByIndividual(userLoginId));
        model.addAttribute("typeDiagnoseResultReadResList", consultingService.getTypeDiagnoseResultByIndividual(userLoginId));

        return "consultant/individual_player_list";
    }
}
