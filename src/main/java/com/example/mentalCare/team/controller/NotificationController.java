package com.example.mentalCare.team.controller;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.director.team.dto.TeamNotificationWriteReq;
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
import org.springframework.web.bind.annotation.PostMapping;
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

        model.addAttribute("userRole", user.getRole());

        return "z-renew/team/noti_list";
    }

    @GetMapping("/{id}")
    public String GetTeamNotificationDetailPage(@PathVariable Long id, Model model) {
        TeamNotificationDetailRes getTeamNotificationDetailRes = notificationService.getTeamNotificationDetailResByTeamNotificationId(id);

        model.addAttribute("teamNotification", getTeamNotificationDetailRes);

        return "z-renew/team/noti_detail";
    }

    /**
     * 공지사항 작성 페이지
     */
    @GetMapping("/form")
    public String notificationWritePage(Model model) {

        model.addAttribute("teamNotificationWriteReq", new TeamNotificationWriteReq());

        return "z-renew/director/noti_add";
    }

    /**
     * 공지사항 작성 서비스
     */
    @PostMapping("/noti")
    public String notificationWrite(TeamNotificationWriteReq teamNotificationWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        notificationService.notificationWrite(userLoginId, teamNotificationWriteReq);

        return "redirect:/noti/list";
    }

}
