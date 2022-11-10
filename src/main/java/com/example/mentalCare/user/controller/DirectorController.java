package com.example.mentalCare.user.controller;

import com.example.mentalCare.diagnose.dto.GetAnswerRes;
import com.example.mentalCare.diagnose.service.DiagnoseService;
import com.example.mentalCare.user.domain.Team;
import com.example.mentalCare.user.dto.GetProfileRes;
import com.example.mentalCare.user.dto.GetUserInfoRes;
import com.example.mentalCare.user.dto.UpdatePlayerRoleReq;
import com.example.mentalCare.user.dto.WriteTeamNotificationReq;
import com.example.mentalCare.user.service.TeamService;
import com.example.mentalCare.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("manage")
@RequiredArgsConstructor
public class DirectorController {

    private final UserAuthService userAuthService;
    private final TeamService teamService;
    private final DiagnoseService diagnoseService;

    /**
     * Consult Page
     */
    @GetMapping("/consult")
    public String consultPage() {
        return "manage/consult";
    }

    /**
     * Player List Page
     * 멤버 리스트 보기
     */
    @GetMapping("/player/list")
    public String playerListPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Team team = userAuthService.getTeamByUserLoginId(userLoginId);

        model.addAttribute("teamName", team.getName());

        List<GetUserInfoRes> getUserInfoResList = teamService.getPlayerOrPendingListByTeamId(team.getId());

        model.addAttribute("playerList", getUserInfoResList);

        return "manage/director_member";
    }

    /**
     * Player Profile Page
     * 멤버 기본프로필 보기
     */
    @GetMapping("/player")
    public String playerPage(@RequestParam String login_id, @RequestParam String role, Model model) {
        GetProfileRes playerProfile = userAuthService.getProfile(login_id, role.toUpperCase());
        model.addAttribute("playerProfile", playerProfile);

        UpdatePlayerRoleReq updatePlayerRoleReq = UpdatePlayerRoleReq.builder()
                .playerId(playerProfile.getId())
                .role(playerProfile.getRole())
                .build();
        model.addAttribute("updatePlayerRoleReq", updatePlayerRoleReq);
        return "manage/member";
    }

    /**
     * Player Role Change
     */
    @PostMapping("/player")
    public String updatePlayer(UpdatePlayerRoleReq updatePlayerRoleReq) {
        userAuthService.updatePlayerRole(updatePlayerRoleReq);

        return "redirect:/manage/player";
    }


    /**
     * Player Recent Diagnose List
     * 팀전체 스트레스 상황 그래프로 보기
     */
    @GetMapping("/test")
    public String testAdminPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Team team = userAuthService.getTeamByUserLoginId(userLoginId);

        // 1. team 내 player user Login_id 찾기
        List<String> playerUserLoginIdList = teamService.getPlayerUserLoginIdListByTeamId(team.getId());

        // 2. 해당 player id 의 최근 answer 찾기
        List<GetAnswerRes> getAnswerResList = new ArrayList<>();

        playerUserLoginIdList.forEach(
                playerUserLoginId -> {
                    Optional<GetAnswerRes> getAnswerResOptional = diagnoseService.getAnswerResByUserLoginId(playerUserLoginId);
                    getAnswerResOptional.ifPresent(
                            getAnswerRes -> {
                                getAnswerResList.add(getAnswerRes);
                            }
                    );
                }
        );

        model.addAttribute("answerList", getAnswerResList);
        return "manage/test_admin";
    }


    /**
     * 팀 전체 공지사항 작성 Page
     */
    @GetMapping("/notification")
    public String writeTeamNotificationPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        WriteTeamNotificationReq writeTeamNotificationReq = new WriteTeamNotificationReq();
        writeTeamNotificationReq.setUserLoginId(userLoginId);

        model.addAttribute("writeTeamNotificationReq", writeTeamNotificationReq);

        return "notificationPage";
    }

    /**
     * 팀 전체 공지사항 작성 서비스
     */
    @PostMapping("/notification")
    public String writeTeamNotificationService(WriteTeamNotificationReq writeTeamNotificationReq) {
        teamService.writeTeamNotification(writeTeamNotificationReq);

        return "redirect:/team/notification";
    }
}
