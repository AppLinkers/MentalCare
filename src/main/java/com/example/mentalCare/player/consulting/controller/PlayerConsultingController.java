package com.example.mentalCare.player.consulting.controller;

import com.example.mentalCare.player.consulting.service.PlayerConsultingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/player/consultant")
@RequiredArgsConstructor
public class PlayerConsultingController {

    private final PlayerConsultingService playerConsultingService;

    @GetMapping("")
    public String requestConsultingPage(Model model) {

        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("consultantInfoReadResList", playerConsultingService.getConsultantInfoReadResList(userLoginId));

        return "z-renew/player/select_consultant";
    }

    @ResponseBody
    @PostMapping("/{id}")
    public void requestConsulting(@PathVariable Long id) {

        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        playerConsultingService.requestConsulting(userLoginId, id);
    }


}
