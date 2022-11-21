package com.example.mentalCare.team.controller;

import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.dto.TeamNotificationDetailRes;
import com.example.mentalCare.team.dto.TeamNotificationInfoRes;
import com.example.mentalCare.team.service.NotificationService;
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
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotificationController {

    private final UserAuthService userAuthService;
    private final NotificationService notificationService;

    @GetMapping("/list")
    public String GetTeamNotificationInfoListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Team team = userAuthService.getTeamByUserLoginId(userLoginId);

        List<TeamNotificationInfoRes> teamNotificationInfoResList = notificationService.getTeamNotificationInfoResListByTeamId(team.getId());

        model.addAttribute("teamNotificationList", teamNotificationInfoResList);

        return "team/noti_list";
    }

    @GetMapping("/{id}")
    public String GetTeamNotificationDetailPage(@PathVariable Long id, Model model) {
        TeamNotificationDetailRes getTeamNotificationDetailRes = notificationService.getTeamNotificationDetailResByTeamNotificationId(id);

        model.addAttribute("teamNotification", getTeamNotificationDetailRes);

        return "team/noti_detail";
    }
}
