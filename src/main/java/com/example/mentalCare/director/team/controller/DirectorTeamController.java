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
     * 팀 정보 페이지
     */
    @GetMapping("/team")
    public String teamInfoPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long teamId = userRepository.findUserByLoginId(userLoginId).get().getTeam().getId();

        model.addAttribute("teamDiagnoseResultList", directorTeamService.getTeamDiagnoseResultList(teamId));
        List<TeamDirectorInfoReadRes> teamDirectorInfoList = directorTeamService.getTeamDirectorInfoList(teamId);
        model.addAttribute("teamDirectorInfoList", teamDirectorInfoList);
        model.addAttribute("teamPlayerInfoList", directorTeamService.getTeamPlayerInfoList(teamId));
        model.addAttribute("directorRoleUpdateReqList", directorTeamService.teamDirectorInfoListToDirectorRoleUpdateListReq(teamDirectorInfoList));

        return "director/team_info";
    }

    /**
     * 감독 권한 변경 서비스
     */
    @PutMapping("/director_role")
    public String changeDirectorRoleList(DirectorRoleUpdateListReq directorRoleUpdateReqList) {
        directorTeamService.changeDirectorRoleList(directorRoleUpdateReqList);

        return "redirect:/director/team";
    }

    /**
     * 선수 상세 정보 페이지
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
     * 선수 포지션 및 권한 변경 서비스
     */
    @PutMapping("/player")
    public String changePlayerInfo(PlayerInfoUpdateReq playerInfoUpdateReq) {
        directorTeamService.changePlayerInfo(playerInfoUpdateReq);
        return "redirect:/director/team";
    }

    /**
     * 공지사항 작성 페이지
     */
    @GetMapping("/noti")
    public String notificationWritePage(Model model) {

        model.addAttribute("teamNotificationWriteReq", new TeamNotificationWriteReq());

        return "director/noti_add";
    }

    /**
     * 공지사항 작성 서비스
     */
    @PostMapping("/noti")
    public String notificationWrite(TeamNotificationWriteReq teamNotificationWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        directorTeamService.notificationWrite(userLoginId, teamNotificationWriteReq);

        return "redirect:/noti/list";
    }
}
