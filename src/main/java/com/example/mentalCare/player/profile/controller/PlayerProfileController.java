package com.example.mentalCare.player.profile.controller;

import com.example.mentalCare.player.profile.dto.PlayerProfileUpdateReq;
import com.example.mentalCare.player.profile.service.PlayerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/player/profile")
@RequiredArgsConstructor
public class PlayerProfileController {

    private final PlayerProfileService playerProfileService;

    /**
     * 선수 프로필 조회 페이지
     */
    @GetMapping("")
    public String profilePage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("profile", playerProfileService.getProfileRead(userLoginId));

        return "player/profile";
    }

    /**
     * 선수 프로필 설정 페이지
     */
    @GetMapping("/setting")
    public String profileSettingPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("profile", playerProfileService.getProfileUpdate(userLoginId));

        model.addAttribute("playerProfileUpdateReq", new PlayerProfileUpdateReq());

        return "player/profile_setting";
    }

    /**
     * 선수 프로필 설정 서비스
     */
    @PutMapping("/setting")
    public String profileSettingService(PlayerProfileUpdateReq playerProfileUpdateReq) throws IOException {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        playerProfileService.UpdateProfile(userLoginId, playerProfileUpdateReq);

        return "redirect:/player/profile";
    }

    

}
