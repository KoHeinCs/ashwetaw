package com.ashwetaw.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestingApi {
    @GetMapping("/test")
    private String test(){
        int a = 3/0;
        return "Test message";
    }
}
