package com.example.mentalCare.user.controller;

import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.dto.DirectorSignUpReq;
import com.example.mentalCare.user.dto.PlayerSignUpReq;
import com.example.mentalCare.user.dto.UpdatePlayerProfileReq;
import com.example.mentalCare.user.dto.UserLoginReq;
import com.example.mentalCare.user.service.TeamService;
import com.example.mentalCare.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
public class UserAuthController {

    private final UserAuthService userAuthService;

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
        String authority = "";
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities() ) {
            authority = grantedAuthority.getAuthority();
        }

        model.addAttribute("profile", userAuthService.getProfile(login_id, authority));

        return "user/profile";
    }

    @GetMapping("/profile/setting")
    public String profileSettingPage(Model model){
        model.addAttribute("updatePlayerProfileReq", new UpdatePlayerProfileReq());

        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("playerProfile", userAuthService.getProfile(login_id, Role.PLAYER.toString()));
        return "user/setting";
    }



    @PostMapping("/profile/setting")
    public String profileSetting(UpdatePlayerProfileReq updatePlayerProfileReq){
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        userAuthService.updatePlayerProfile(login_id, updatePlayerProfileReq);
        return "redirect:/profile";
    }


    /**
     * 팀 전체 공지사항 정보 전체 조회
     */


    /**
     * 팀 전체공지사항 단건 조회
     */

}
