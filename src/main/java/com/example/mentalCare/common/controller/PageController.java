package com.example.mentalCare.common.controller;

import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PlayerRepository playerRepository;

    /**
     * 네비게이션 바 페이지
     */
    @GetMapping("/nav")
    public String callNav(Model model) {
        String authority = "";
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            authority = grantedAuthority.getAuthority();
        }

        model.addAttribute("userRole", authority);

        if (authority.equals("PLAYER")) {
            String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
            Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

            model.addAttribute("ableRequestConsulting", player.ableRequestConsulting());
        }

        return "z-renew/common/nav";
    }

    /**
     * 메인 페이지
     */
    @GetMapping("/")
    public String index(Model model){
        String authority = "";
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            authority = grantedAuthority.getAuthority();
        }

        model.addAttribute("userRole", authority);
        return "z-renew/common/main";
    }

}
