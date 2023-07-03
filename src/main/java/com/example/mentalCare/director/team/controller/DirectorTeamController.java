package com.example.mentalCare.director.team.controller;

import com.example.mentalCare.director.team.dto.*;
import com.example.mentalCare.director.team.service.DirectorTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/director")
@RequiredArgsConstructor
public class DirectorTeamController {

    private final DirectorTeamService directorTeamService;

    /**
     * 선수 상세 정보 페이지
     */
    @GetMapping("/player/{id}")
    public String teamPlayerPage(Model model, @PathVariable Long id) {

        TeamPlayerDetailReadRes teamPlayerDetail = directorTeamService.getTeamPlayerDetail(id);
        model.addAttribute("teamPlayerDetail", teamPlayerDetail);

        model.addAttribute("playerInfoUpdateReq", teamPlayerDetail.toPlayerInfoUpdateReq());

        return "director/player_info";
    }

    /**
     * 선수 포지션 및 권한 변경 서비스
     */
    @PutMapping("/player")
    public String changePlayerInfo(PlayerInfoUpdateReq playerInfoUpdateReq) {
        directorTeamService.changePlayerInfo(playerInfoUpdateReq);
        return "redirect:/director/profile";
    }

    /**
     * 감독 상세 정보 페이지
     */
    @GetMapping("/director/{id}")
    public String teamDirectorPage(Model model, @PathVariable Long id) {

        TeamDirectorInfoReadRes teamDirectorDetail = directorTeamService.getTeamDirectorDetail(id);
        model.addAttribute("teamDirectorDetail", teamDirectorDetail);

        model.addAttribute("directorRoleUpdateReq", teamDirectorDetail.toDirectorRoleUpdateReq());

        return "director/player_info";
    }

    /**
     * 감독 권한 변경 서비스
     */
    @PutMapping("/director_role")
    public String changeDirectorRoleList(DirectorRoleUpdateReq directorRoleUpdateReq) {
        directorTeamService.changeDirectorRole(directorRoleUpdateReq);

        return "redirect:/director/team";
    }

}
