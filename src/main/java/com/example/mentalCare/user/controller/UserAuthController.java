package com.example.mentalCare.user.controller;

import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.type.Position;
import com.example.mentalCare.user.dto.DirectorSignUpReq;
import com.example.mentalCare.user.dto.PlayerSignUpReq;
import com.example.mentalCare.user.dto.UserLoginReq;
import com.example.mentalCare.user.dto.UserSignUpReq;
import com.example.mentalCare.user.service.UserAuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    /**
     * Login Page
     */
    @GetMapping("/login")
    public String loginPage(UserLoginReq request) {
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

        return "redirect:user/login";
    }

    /**
     * SignUp Page
     */
    @GetMapping("/sign_up")
    public String signUpPage(PlayerSignUpReq playerSignUpReq, DirectorSignUpReq directorSignUpReq) {
        return "user/sign_up";
    }

    /**
     * SignUp Service
     */
    @PostMapping("/sign_up")
    public String signUp(UserSignUpReq request) {
        userAuthService.signUp(request);
        return "redirect:user/login";
    }

    /**
     * Profile Page
     */
    @GetMapping("/profile")
    public String profilePage() {
        return "user/profile";
    }
}
