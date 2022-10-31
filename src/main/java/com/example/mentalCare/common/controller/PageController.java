package com.example.mentalCare.common.controller;

import com.example.mentalCare.user.controller.UserAuthController;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final UserAuthService userAuthService;

    @GetMapping("/")
    public String index(){
        return "common/main";
    }

    @GetMapping("/nav")
    public String callNav(Model model){
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userAuthService.findUserByUserId(userLoginId);
        String role = user.getRole()+"";
        model.addAttribute("userRole",role);
        return "common/nav";}


    @GetMapping("/testAdmin")
    public String testAdmin(){
        return "manage/test_admin";
    }




}
