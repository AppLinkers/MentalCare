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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String loginPage(UserLoginReq userLoginReq) {
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
    public String signUpPage(Model model, PlayerSignUpReq playerSignUpReq, DirectorSignUpReq directorSignUpReq) {
        model.addAttribute("playerSignUpReq", playerSignUpReq);
        model.addAttribute("directorSignUpReq", directorSignUpReq);
        return "user/sign_up";
    }
    /**
     * Player SignUp Service
     */
    @PostMapping("/sign_up/player")
    public String playerSignUp(@ModelAttribute PlayerSignUpReq request, Model model) {
        userAuthService.signUp(request);
        return "redirect:/login";
    }

    /**
     * Director SignUp Service
     */
    @PostMapping("/sign_up/director")
    public String directorSignUp(@ModelAttribute DirectorSignUpReq request, Model model) {
        userAuthService.signUp(request);
        return "redirect:/login";
    }

    /**
     * Profile Page
     */
    @GetMapping("/profile")
    public String profilePage() {
        return "user/profile";
    }
}
