package com.example.mentalCare.user.controller;


import com.example.mentalCare.user.dto.UserLoginReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/")
    public String index(){
        return "init";
    }


}
