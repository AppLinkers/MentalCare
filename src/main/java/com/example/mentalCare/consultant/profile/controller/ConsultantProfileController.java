package com.example.mentalCare.consultant.profile.controller;

import com.example.mentalCare.consultant.consulting.service.ConsultingService;
import com.example.mentalCare.consultant.profile.dto.ConsultantProfileUpdateReq;
import com.example.mentalCare.consultant.profile.dto.ConsultantProfileUpdateRes;
import com.example.mentalCare.consultant.profile.service.ConsultantProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/consultant/profile")
@RequiredArgsConstructor
public class ConsultantProfileController {

    private final ConsultantProfileService consultantProfileService;
    private final ConsultingService consultingService;

    /**
     * 상담가 프로필 페이지
     */
    @GetMapping("")
    public String profilePage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("profile", consultantProfileService.getProfileRead(userLoginId));
        model.addAttribute("teamPlayerDiagnoseResultDateResList", consultingService.getTeamPlayerDiagnoseResultByDate(userLoginId));
        model.addAttribute("individualPlayerDiagnoseResultDateResList", consultingService.getIndividualPlayerDiagnoseResultByDate(userLoginId));

        return "consultant/profile";
    }

    /**
     * 상담가 프로필 설정 페이지
     */
    @GetMapping("/setting")
    public String profileSettingPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        ConsultantProfileUpdateRes consultantProfileUpdateRes = consultantProfileService.getProfileUpdate(userLoginId);

        model.addAttribute("profile", consultantProfileUpdateRes);

        model.addAttribute("consultantProfileUpdateReq", consultantProfileUpdateRes.toConsultantProfileUpdateReq());

        return "consultant/profile_edit";
    }

    /**
     * 상담가 프로필 설정 서비스
     */
    @PutMapping("/setting")
    public String profileSettingService(ConsultantProfileUpdateReq consultantProfileUpdateReq) throws IOException {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        consultantProfileService.updateProfile(userLoginId, consultantProfileUpdateReq);

        return "redirect:/consultant/profile";
    }
}
