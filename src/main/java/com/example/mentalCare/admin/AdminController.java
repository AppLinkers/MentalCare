package com.example.mentalCare.admin;

import com.example.mentalCare.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {



    @GetMapping("/admin/member")
    public String adminMember(Model model){
        return "/admin/member";
    }

    @GetMapping("/admin/teamcode")
    public String adminTeamCode(Model model){
        return "/admin/team_code";
    }

    @GetMapping("/admin/teamcode/add")
    public String adminTeamCodeForm(Model model){
        return "/admin/team_code_form";
    }

}
