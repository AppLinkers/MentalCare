package com.example.mentalCare.admin.controller;

import com.example.mentalCare.admin.dto.TeamAddReq;
import com.example.mentalCare.admin.dto.UserInfoRes;
import com.example.mentalCare.admin.dto.UserRoleUpdateListReq;
import com.example.mentalCare.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 사용자 목록 페이지
     */
    @GetMapping("/user")
    public String userPage(Model model) {
        List<UserInfoRes> userInfoResList = adminService.getUserInfoResList();
        model.addAttribute("userInfoResList", userInfoResList);
        model.addAttribute("userRoleUpdateReqList", adminService.userInfoListToUserRoleUpdateListReq(userInfoResList));
        model.addAttribute("teamInfoResList", adminService.getTeamInfoResList());
        return "z-renew/admin/user";
    }

    /**
     * 사용자 권한 변경 서비스
     */
    @PutMapping("/user")
    public String changeUserRoleList(UserRoleUpdateListReq userRoleUpdateListReq) {

       String role = userRoleUpdateListReq.getUserRoleUpdateReqList().get(12).getRole()+"";
        System.out.println(role);
        adminService.changeUserRoleList(userRoleUpdateListReq);

        return "redirect:/admin/user";
    }

    /**
     * 팀 목록 페이지
     */
    @GetMapping("/team")
    public String teamPage(Model model) {
        model.addAttribute("teamInfoResList", adminService.getTeamInfoResList());

        return "z-renew/admin/team";
    }

    /**
     * 팀 삭제 서비스
     */
    @DeleteMapping("/team/{id}")
    public String deleteTeam(@PathVariable Long id) {
        adminService.deleteTeam(id);

        return "redirect:/admin/team";
    }

    /**
     * 팀 추가 페이지
     */
    @GetMapping("/team/form")
    public String teamFormPage(TeamAddReq teamAddReq) {
        return "z-renew/admin/team_form";
    }

    /**
     * 팀 추가 서비스
     */
    @PostMapping("/team")
    public String addTeam(TeamAddReq teamAddReq) {
        adminService.addTeam(teamAddReq);

        return "redirect:/admin/team";
    }
}
