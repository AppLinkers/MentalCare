package com.example.mentalCare.director.profile.controller;

import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateReq;
import com.example.mentalCare.director.profile.service.DirectorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/director/profile")
@RequiredArgsConstructor
public class DirectorProfileController {

    private final DirectorProfileService directorProfileService;

    /**
     * 감독 프로필 조회 페이지
     */
    @GetMapping("")
    public String profilePage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("profile", directorProfileService.getProfileRead(userLoginId));

        return "director/profile";
    }

    /**
     * 감독 프로필 설정 페이지
     */
    @GetMapping("/setting")
    public String profileSettingPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("profile", directorProfileService.getProfileUpdate(userLoginId));

        model.addAttribute("directorProfileUpdateReq", new DirectorProfileUpdateReq());

        return "director/profile_setting";
    }

    /**
     * 감독 프로필 설정 서비스
     */
    @PutMapping("/setting")
    public String profileSettingService(DirectorProfileUpdateReq directorProfileUpdateReq) throws IOException {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        directorProfileService.UpdateProfile(userLoginId, directorProfileUpdateReq);

        return "redirect:/director/profile";
    }
}
