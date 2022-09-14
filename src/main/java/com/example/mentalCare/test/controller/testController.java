package com.example.mentalCare.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class testController {

    /**
     * Test Page
     */
    @GetMapping("/test")
    public String testPage() {
        return "test/test";
    }

    /**
     * Test Result Page
     */
    @GetMapping("/test/result")
    public String testResultPage() {
        return "test/test_result";
    }
}
