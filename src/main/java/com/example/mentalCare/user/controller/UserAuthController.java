package com.example.mentalCare.user.controller;

import com.example.mentalCare.user.dto.DirectorSignUpReq;
import com.example.mentalCare.user.dto.GetPlayerRes;
import com.example.mentalCare.user.dto.PlayerSignUpReq;
import com.example.mentalCare.user.dto.UserLoginReq;
import com.example.mentalCare.user.service.UserAuthService;
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
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public String profilePage(Model model) throws ParseException {
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        GetPlayerRes getPlayerRes = userAuthService.getUserData(login_id);
        int Ddays = 0;
        //date calc
        if(getPlayerRes.getNextMatch() != null){
            System.out.println("match calculation");
            String todayFm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(dateFormat.parse(getPlayerRes.getNextMatch()).getTime());
            Date today = new Date(dateFormat.parse(todayFm).getTime());
            long calculate = date.getTime() - today.getTime();
            Ddays = (int) (calculate / ( 24*60*60*1000));

        }
        model.addAttribute("dday", Ddays);
        model.addAttribute("player",getPlayerRes);
        return "user/profile";
    }

    @GetMapping("/profile/update")
    public String profileUpdate(Model model, PlayerSignUpReq playerSignUpReq){
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        GetPlayerRes getPlayerRes = userAuthService.getUserData(login_id);
        model.addAttribute("player",getPlayerRes);
        return "user/setting";
    }



    @PostMapping("/profile/updateProfile")
    public String updateProfile(PlayerSignUpReq playerSignUpReq){
        String login_id = SecurityContextHolder.getContext().getAuthentication().getName();
        userAuthService.updateProfile(login_id, playerSignUpReq);
        return "redirect:/profile";
    }
}
