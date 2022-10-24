package com.example.mentalCare.user.controller;

import com.example.mentalCare.user.domain.Team;
import com.example.mentalCare.user.dto.GetPlayerProfileRes;
import com.example.mentalCare.user.dto.UpdatePlayerRoleReq;
import com.example.mentalCare.user.service.TeamService;
import com.example.mentalCare.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("manage")
@RequiredArgsConstructor
public class DirectorController {

    private final UserAuthService userAuthService;
    private final TeamService teamService;

    /**
     * Consult Page
     */
    @GetMapping("/consult")
    public String consultPage() {
        return "manage/consult";
    }

    /**
     * Member Page
     */
    @GetMapping("/player")
    public String memberPage(Model model) {
        List<UpdatePlayerRoleReq> updatePlayerRoleReqList = new ArrayList<>();

        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Team team = userAuthService.getTeamByUserLoginId(userLoginId);

        List<GetPlayerProfileRes> getPlayerProfileResList = teamService.getPlayerProfileResListByTeamId(team.getId());
        model.addAttribute("playerList", getPlayerProfileResList);

        getPlayerProfileResList.forEach(
                getPlayerProfileRes -> {
                    UpdatePlayerRoleReq updatePlayerRoleReq = UpdatePlayerRoleReq.builder()
                            .playerId(getPlayerProfileRes.getId())
                            .role(getPlayerProfileRes.getRole())
                            .build();
                    updatePlayerRoleReqList.add(updatePlayerRoleReq);
                }
        );
        model.addAttribute("updatePlayerRoleReqList", updatePlayerRoleReqList);
        return "manage/member";
    }

    /**
     * Player Role Change
     */
    @PostMapping("/player")
    public String updatePlayer(List<UpdatePlayerRoleReq> updatePlayerRoleReqList) {
        teamService.updatePlayerRole(updatePlayerRoleReqList);
        return "redirect:/manage/player";
    }

    /**
     * Test Admin Page
     */
    @GetMapping("/test")
    public String testAdminPage() {
        return "manage/test_admin";
    }

}
