package com.example.mentalCare.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/")
    public String index(){
        return "common/main";
    }

    @GetMapping("/nav")
    public String callNav(){return "common/nav";}





}
