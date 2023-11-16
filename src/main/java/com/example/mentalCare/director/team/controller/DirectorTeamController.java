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

        model.addAttribute("playerRoleUpdateReq", teamPlayerDetail.toPlayerRoleUpdateReq());

        return "z-renew/director/player_manage";
    }

    /**
     * 선수 포지션 및 권한 변경 서비스
     */
    @PutMapping("/player")
    public String changePlayerRole(PlayerRoleUpdateReq playerRoleUpdateReq) {
        directorTeamService.changePlayerRole(playerRoleUpdateReq);
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

        return "z-renew/director/director_manage";
    }

    /**
     * 감독 권한 변경 서비스
     */
    @PutMapping("/director")
    public String changeDirectorRole(DirectorRoleUpdateReq directorRoleUpdateReq) {
        directorTeamService.changeDirectorRole(directorRoleUpdateReq);

        return "redirect:/director/profile";
    }

    /**
     * 상담가 상세 정보 페이지
     */
    @GetMapping("/consultant/{id}")
    public String teamConsultantPage(Model model, @PathVariable Long id) {

        TeamConsultantInfoReadRes teamConsultantDetail = directorTeamService.getTeamConsultantDetail(id);
        model.addAttribute("teamConsultantDetail", teamConsultantDetail);

        model.addAttribute("consultantRoleUpdateReq", teamConsultantDetail.toConsultantRoleUpdateReq());

        return "z-renew/director/consultant_manage";
    }

    /**
     * 상담가 권한 변경 서비스
     */
    @PutMapping("/consultant")
    public String changeConsultantRole(ConsultantRoleUpdateReq consultantRoleUpdateReq) {
        directorTeamService.changeConsultantRole(consultantRoleUpdateReq);

        return "redirect:/director/profile";
    }

}
