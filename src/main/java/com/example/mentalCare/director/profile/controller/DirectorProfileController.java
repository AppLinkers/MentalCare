package com.example.mentalCare.director.profile.controller;

import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateReq;
import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateRes;
import com.example.mentalCare.director.profile.service.DirectorProfileService;
import com.example.mentalCare.director.team.service.DirectorTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/director/profile")
@RequiredArgsConstructor
public class DirectorProfileController {

    private final DirectorProfileService directorProfileService;
    private final DirectorTeamService directorTeamService;

    private final UserRepository userRepository;

    /**
     * 감독 프로필 조회 페이지
     */
    @GetMapping("")
    public String profilePage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long teamId = userRepository.findUserByLoginId(userLoginId).get().getTeam().getId();

        model.addAttribute("profile", directorProfileService.getProfileRead(userLoginId));
        model.addAttribute("teamDiagnoseResultList", directorTeamService.getTeamDiagnoseResultList(teamId));
        model.addAttribute("teamDirectorInfoList", directorTeamService.getTeamDirectorInfoList(teamId));
        model.addAttribute("teamPlayerInfoList", directorTeamService.getTeamPlayerInfoList(teamId));

        return "z-renew/director/profile";
    }

    /**
     * 감독 프로필 설정 페이지
     */
    @GetMapping("/setting")
    public String profileSettingPage(Model model) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        DirectorProfileUpdateRes directorProfileUpdateRes = directorProfileService.getProfileUpdate(userLoginId);

        model.addAttribute("profile", directorProfileUpdateRes);

        model.addAttribute("directorProfileUpdateReq", directorProfileUpdateRes.toDirectorProfileUpdateReq());

        return "z-renew/director/profile_edit";
    }

    /**
     * 감독 프로필 설정 서비스
     */
    @PutMapping("/setting")
    public String profileSettingService(DirectorProfileUpdateReq directorProfileUpdateReq) throws IOException {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        directorProfileService.updateProfile(userLoginId, directorProfileUpdateReq);

        return "redirect:/director/profile";
    }
}
