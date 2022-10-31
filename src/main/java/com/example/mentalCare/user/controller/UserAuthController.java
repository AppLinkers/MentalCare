package com.example.mentalCare.user.controller;

import com.example.mentalCare.diagnose.dto.GetDiagnoseInfoRes;
import com.example.mentalCare.user.domain.Player;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.UserDetail;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.dto.*;
import com.example.mentalCare.user.service.TeamService;
import com.example.mentalCare.user.service.UserAuthService;
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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;
    private final TeamService teamService;

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
        model.addAttribute("playerSignUpReq", new PlayerSignUpReq());
        model.addAttribute("directorSignUpReq", new DirectorSignUpReq());

        return "user/sign_up";
    }
    /**
     * Player SignUp Service
     */
    @PostMapping("/sign_up/player")
    public String playerSignUp(PlayerSignUpReq request) {
        userAuthService.signUp(request);
        return "redirect:/login";
    }

    /**
     * Director SignUp Service
     */
    @PostMapping("/sign_up/director")
    public String directorSignUp(DirectorSignUpReq request) {
        userAuthService.signUp(request);
        return "redirect:/login";
    }

    /**
     * Profile Page
     */
    @GetMapping("/profile")
    public String profilePage(Model model) {
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userAuthService.findUserByUserId(login_id);
        GetPlayerProfileRes getPlayerProfileRes = null;

        if(user.getRole() == Role.PLAYER){
            getPlayerProfileRes = userAuthService.getPlayerProfile(login_id);
        }else if(user.getRole() == Role.DIRECTOR){
            model.addAttribute("directorProfile", user);
        }else{

        }
        String userRole = user.getRole()+"";
        model.addAttribute("userRole", userRole);
        model.addAttribute("playerProfile",getPlayerProfileRes);
        return "user/profile";
    }

    @GetMapping("/profile/setting")
    public String profileSettingPage(Model model){
        model.addAttribute("updatePlayerProfileReq", new UpdatePlayerProfileReq());

        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("playerProfile", userAuthService.getPlayerProfile(login_id));
        return "user/setting";
    }



    @PostMapping("/profile/setting")
    public String profileSetting(UpdatePlayerProfileReq updatePlayerProfileReq){
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        userAuthService.updateProfile(login_id, updatePlayerProfileReq);
        return "redirect:/profile";
    }

}
