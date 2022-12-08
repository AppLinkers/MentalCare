package com.example.mentalCare.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    /**
     * 메인 페이지
     */
    @GetMapping("/")
    public String index(){
        return "common/main";
    }

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
        return "common/nav";
    }


}
