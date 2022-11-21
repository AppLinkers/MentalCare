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

    @GetMapping("/")
    public String index(){
        return "common/main";
    }

    @GetMapping("/nav")
    public String callNav(Model model){
        String authority = "";
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities() ) {
            authority = grantedAuthority.getAuthority();
        }

        model.addAttribute("userRole", authority);
        return "common/nav";
    }


}
