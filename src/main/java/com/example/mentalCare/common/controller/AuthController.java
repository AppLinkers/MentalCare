package com.example.mentalCare.common.controller;


import com.example.mentalCare.common.dto.*;
import com.example.mentalCare.common.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입 페이지
     */
    @GetMapping("/sign_up")
    public String signUpPage(Model model) {
        model.addAttribute("signUpPlayerReq", new SignUpPlayerReq());
        model.addAttribute("signUpDirectorReq", new SignUpDirectorReq());

        return "z-renew/common/sign_up";
    }

    /**
     * 선수 회원가입 서비스
     */
    @PostMapping("/sign_up/player")
    public String playerSignUp(SignUpPlayerReq request) {
        authService.signUp(request);
        return "redirect:/login";
    }

    /**
     * 감독 회원가입 서비스
     */
    @PostMapping("/sign_up/director")
    public String directorSignUp(SignUpDirectorReq request) {
        authService.signUp(request);
        return "redirect:/login";
    }

    /**
     * 컨설턴트 회원가입 서비스
     */
    @PostMapping("/sign_up/consultant")
    public String consultantSignUp(SignUpConsultantReq request) {
        authService.signUp(request);
        return "redirect:/login";
    }

    @GetMapping("/pwChange")
    public String changePasswordPage(ChangePwReq changePwReq){
        return "z-renew/common/change_password";
    }

    @PutMapping("/pwChange")
    public String changePassword(ChangePwReq changePwReq){
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        authService.changePassword(login_id, changePwReq);
        return "redirect:/logout";
    }

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String loginPage(Model model) {

        model.addAttribute("userLoginReq", new UserLoginReq());
        return "z-renew/common/login";
    }

    /**
     * 로그아웃 서비스
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";
    }
}
