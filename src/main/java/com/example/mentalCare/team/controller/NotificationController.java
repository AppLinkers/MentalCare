package com.example.mentalCare.team.controller;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.dto.TeamNotificationDetailRes;
import com.example.mentalCare.team.dto.TeamNotificationInfoRes;
import com.example.mentalCare.team.service.NotificationService;
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

    private final NotificationService notificationService;

    private final UserRepository userRepository;

    @GetMapping("/list")
    public String GetTeamNotificationInfoListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByLoginId(userLoginId).get();
        Team team = user.getTeam();

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
