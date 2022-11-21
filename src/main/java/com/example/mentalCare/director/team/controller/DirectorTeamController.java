package com.example.mentalCare.director.team.controller;

import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.director.team.dto.DirectorRoleUpdateReq;
import com.example.mentalCare.director.team.dto.TeamNotificationWriteReq;
import com.example.mentalCare.user.dto.UpdatePlayerRoleReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/director")
@RequiredArgsConstructor
public class DirectorTeamController {

    private final UserRepository userRepository;

    /**
     * Team Info And Member Page
     */
    @GetMapping("/team")
    public String teamInfoPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long teamId = userRepository.findUserByLoginId(userLoginId).get().getTeam().getId();

        model.addAttribute("teamDiagnoseResultList", "");
        model.addAttribute("teamDirectorInfoList", "");
        model.addAttribute("teamPlayerInfoList", "");

        model.addAttribute("directorRoleUpdateReqList", "");

        return "director/team_info";
    }

    /**
     * Change Director Role List Service
     */
    @PutMapping("/director_role")
    public void changeDirectorRoleList(List<DirectorRoleUpdateReq> directorRoleUpdateReqList) {

    }

    /**
     * Team Player Info Page
     */
    @GetMapping("/player/{id}")
    public String teamPlayerPage(Model model, @PathVariable Long id) {

        model.addAttribute("teamPlayerDetail", "");

        model.addAttribute("playerRoleUpdateReq", "");

        return "director/player_info";
    }

    /**
     * Change Player Role Service
     */
    @PutMapping("/player")
    public void changePlayerRole(UpdatePlayerRoleReq updatePlayerRoleReq) {


    }

    /**
     * Notification Write Page
     */
    @GetMapping("/noti")
    public String notificationWritePage(Model model) {

        model.addAttribute("teamNotificationWriteReq", "");

        return "director/noti_add";
    }

    /**
     * Notification Write Service
     */
    @PostMapping("/noti")
    public void notificationWrite(TeamNotificationWriteReq teamNotificationWriteReq) {


    }
}
