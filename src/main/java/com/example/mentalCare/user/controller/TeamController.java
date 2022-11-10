package com.example.mentalCare.user.controller;

import com.example.mentalCare.user.domain.Team;
import com.example.mentalCare.user.dto.GetTeamNotificationDetailRes;
import com.example.mentalCare.user.dto.GetTeamNotificationInfoRes;
import com.example.mentalCare.user.service.TeamService;
import com.example.mentalCare.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("team")
@RequiredArgsConstructor
public class TeamController {

    private final UserAuthService userAuthService;
    private final TeamService teamService;

    @GetMapping("/notification")
    public String GetTeamNotificationInfoListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Team team = userAuthService.getTeamByUserLoginId(userLoginId);

        List<GetTeamNotificationInfoRes> teamNotificationInfoResList = teamService.getTeamNotificationInfoResListByTeamId(team.getId());

        model.addAttribute("teamNotificationInfoResList", teamNotificationInfoResList);

        return "teamNotificationInfoListPage";
    }

    @GetMapping("/notification/{id}")
    public String GetTeamNotificationDetailPage(@PathVariable Long id, Model model) {
        GetTeamNotificationDetailRes getTeamNotificationDetailRes = teamService.getTeamNotificationDetailResByTeamNotificationId(id);

        model.addAttribute("getTeamNotificationDetailRes", getTeamNotificationDetailRes);

        return "teamNotificationDetailPage";
    }
}
