package com.example.mentalCare.manage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("director")
@RequiredArgsConstructor
public class manageController {

    /**
     * Consult Page
     */
    @GetMapping("/consult")
    public String consultPage() {
        return "manage/consult";
    }

    /**
     * Member Page
     */
    @GetMapping("/member")
    public String memberPage() {
        return "manage/member";
    }

    /**
     * Test Admin Page
     */
    @GetMapping("/test")
    public String testAdminPage() {
        return "manage/test_admin";
    }

}
