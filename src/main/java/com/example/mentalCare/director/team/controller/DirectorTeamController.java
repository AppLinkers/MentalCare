package com.example.mentalCare.director.team.controller;

import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.director.team.dto.*;
import com.example.mentalCare.director.team.service.DirectorTeamService;
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
    private final DirectorTeamService directorTeamService;

    /**
     * Team Info And Member Page
     */
    @GetMapping("/team")
    public String teamInfoPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long teamId = userRepository.findUserByLoginId(userLoginId).get().getTeam().getId();

        model.addAttribute("teamDiagnoseResultList", directorTeamService.getTeamDiagnoseResultList(teamId));
        List<TeamDirectorInfoReadRes> teamDirectorInfoList = directorTeamService.getTeamDirectorInfoList(teamId);
        model.addAttribute("teamDirectorInfoList", teamDirectorInfoList);
        model.addAttribute("teamPlayerInfoList", directorTeamService.getTeamPlayerInfoList(teamId));
        model.addAttribute("directorRoleUpdateReqList", directorTeamService.teamDirectorInfoListToDirectorRoleUpdateReqList(teamDirectorInfoList));

        return "director/team_info";
    }

    /**
     * Change Director Role List Service
     */
    @PutMapping("/director_role")
    public String changeDirectorRoleList(DirectorRoleUpdateListReq directorRoleUpdateReqList) {
        directorTeamService.changeDirectorRoleList(directorRoleUpdateReqList);

        return "redirect:/director/team";
    }

    /**
     * Team Player Info Page
     */
    @GetMapping("/player/{id}")
    public String teamPlayerPage(Model model, @PathVariable Long id) {

        TeamPlayerDetailReadRes teamPlayerDetail = directorTeamService.getTeamPlayerDetail(id);
        model.addAttribute("teamPlayerDetail", teamPlayerDetail);
        model.addAttribute("playerRoleUpdateReq", directorTeamService.teamPlayerDetailReadResToPlayerInfoUpdateReq(teamPlayerDetail));

        model.addAttribute("playerInfoUpdateReq", new PlayerInfoUpdateReq());

        return "director/player_info";
    }

    /**
     * Change Player Role Service
     */
    @PutMapping("/player")
    public String changePlayerInfo(PlayerInfoUpdateReq playerInfoUpdateReq) {
        directorTeamService.changePlayerInfo(playerInfoUpdateReq);
        return "redirect:/director/team";
    }

    /**
     * Notification Write Page
     */
    @GetMapping("/noti")
    public String notificationWritePage(Model model) {

        model.addAttribute("teamNotificationWriteReq", new TeamNotificationWriteReq());

        return "director/noti_add";
    }

    /**
     * Notification Write Service
     */
    @PostMapping("/noti")
    public String notificationWrite(TeamNotificationWriteReq teamNotificationWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        directorTeamService.notificationWrite(userLoginId, teamNotificationWriteReq);

        return "redirect:/noti/list";
    }
}
