package com.example.mentalCare.common.controller;


import com.example.mentalCare.common.dto.SignUpDirectorReq;
import com.example.mentalCare.common.dto.SignUpPlayerReq;
import com.example.mentalCare.common.dto.UserLoginReq;
import com.example.mentalCare.common.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Login Page
     */
    @GetMapping("/login")
    public String loginPage(Model model) {

        model.addAttribute("userLoginReq", new UserLoginReq());
        return "user/login";
    }

    /**
     * Logout Service
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";
    }

    /**
     * SignUp Page
     */
    @GetMapping("/sign_up")
    public String signUpPage(Model model) {
        model.addAttribute("signUpPlayerReq", new SignUpPlayerReq());
        model.addAttribute("signUpDirectorReq", new SignUpDirectorReq());

        return "user/sign_up";
    }
    /**
     * Player SignUp Service
     */
    @PostMapping("/sign_up/player")
    public String playerSignUp(SignUpPlayerReq request) {
        authService.signUp(request);
        return "redirect:/login";
    }

    /**
     * Director SignUp Service
     */
    @PostMapping("/sign_up/director")
    public String directorSignUp(SignUpDirectorReq request) {
        authService.signUp(request);
        return "redirect:/login";
    }
}
