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
    UserAuthService userAuthService;

    @GetMapping("/")
    public String index(){
        return "common/main";
    }

    @GetMapping("/nav")
    public String callNav(){
        return "common/nav";}


    @GetMapping("/testAdmin")
    public String testAdmin(){
        return "manage/test_admin";
    }




}
