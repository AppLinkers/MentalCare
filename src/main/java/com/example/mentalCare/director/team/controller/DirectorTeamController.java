package com.example.mentalCare.director.team.controller;

import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.director.team.dto.*;
import com.example.mentalCare.director.team.service.DirectorTeamService;
import com.example.mentalCare.player.test.dto.DiagnoseReadRes;
import com.example.mentalCare.player.test.dto.DiagnoseWriteReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        DirectorRoleUpdateReqList directorRoleUpdateReqList = new DirectorRoleUpdateReqList();
        List<DirectorRoleUpdateReq> list = new ArrayList<>();
        for(TeamDirectorInfoReadRes teamDirector : teamDirectorInfoList){
            DirectorRoleUpdateReq directorRoleUpdateReq = new DirectorRoleUpdateReq();
            directorRoleUpdateReq.setRole(teamDirector.getRole());
            directorRoleUpdateReq.setId(teamDirector.getId());
            list.add(directorRoleUpdateReq);
        }
        directorRoleUpdateReqList.setDirectorRoleUpdateReqList(list);
        model.addAttribute("directorRoleUpdateReqList", directorRoleUpdateReqList);


        return "director/team_info";
    }

    /**
     * Change Director Role List Service
     */
    @PostMapping("/director_role")
    public String changeDirectorRoleList(DirectorRoleUpdateReqList directorRoleUpdateReqList) {
        directorTeamService.changeDirectorRoleList(directorRoleUpdateReqList.getDirectorRoleUpdateReqList());
        return "redirect:team";
    }

    /**
     * Team Player Info Page
     */
    @GetMapping("/player/{id}")
    public String teamPlayerPage(Model model, @PathVariable Long id, PlayerInfoUpdateReq playerInfoUpdateReq) {

        TeamPlayerDetailReadRes teamPlayerDetail = directorTeamService.getTeamPlayerDetail(id);
        model.addAttribute("teamPlayerDetail", teamPlayerDetail);

        model.addAttribute("playerRoleUpdateReq", directorTeamService.teamPlayerDetailReadResToPlayerInfoUpdateReq(teamPlayerDetail));

        return "director/player_info";
    }

    /**
     * Change Player Role Service
     */
    @PostMapping("/player")
    public String changePlayerInfo(PlayerInfoUpdateReq playerInfoUpdateReq) {
        directorTeamService.changePlayerInfo(playerInfoUpdateReq);
        return "redirect:team";
    }

    /**
     * Notification Write Page
     */
    @GetMapping("/noti")
    public String notificationWritePage(Model model, TeamNotificationWriteReq teamNotificationWriteReq) {


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
