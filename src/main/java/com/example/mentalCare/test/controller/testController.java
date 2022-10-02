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

    @GetMapping("/myResult")
    public String myResult() {
        return "test/my_result";
    }

    @GetMapping("/testing")
    public String goToTest() {
        return "test/testing";
    }

    /**
     * Test Result Page
     */
    @GetMapping("/testList")
    public String testResultPage() {
        return "test/test_result";
    }
}
