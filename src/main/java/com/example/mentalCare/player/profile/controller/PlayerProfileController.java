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

@Controller
@RequestMapping("/player/profile")
@RequiredArgsConstructor
public class PlayerProfileController {

    private final PlayerProfileService playerProfileService;

    /**
     * Profile Page
     */
    @GetMapping("")
    public String profilePage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("profile", playerProfileService.getProfileRead(userLoginId));

        return "player/profile";
    }

    /**
     * Profile Setting Page
     */
    @GetMapping("/setting")
    public String profileSettingPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("profile", playerProfileService.getProfileUpdate(userLoginId));

        model.addAttribute("playerProfileUpdateReq", new PlayerProfileUpdateReq());

        return "player/profile_setting";
    }

    /**
     * Profile Setting Service
     */
    @PutMapping("/setting")
    public void profileSettingService(PlayerProfileUpdateReq playerProfileUpdateReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        playerProfileService.UpdateProfile(userLoginId, playerProfileUpdateReq);
    }


}
