package com.example.mentalCare.consultant.consulting.controller;

import com.example.mentalCare.consultant.consulting.service.ConsultingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consultant/individual/player")
@RequiredArgsConstructor
public class ConsultantConsultingIndividualPlayerController {

    private final ConsultingService consultingService;

    @GetMapping("/request")
    public String individualPlayerConsultingRequestPage(Model model) {

        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("playerInfoReadResList", consultingService.getIndividualPlayerConsultingRequest(userLoginId));

        return "z-renew/consultant/individual_player_request";
    }

    @ResponseBody
    @PostMapping("/{playerId}")
    public void individualPlayerConsultingRequest(
            @PathVariable Long playerId,
            @RequestParam(name = "accept", required = true) Boolean accept
    ) {

        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        consultingService.individualPlayerConsultingRequest(userLoginId, playerId, accept);
    }
}
