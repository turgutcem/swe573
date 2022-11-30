package com.swe573.swe573.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TimelineController {

    @GetMapping("/")
    public String home(@RequestParam(required = false) Long page){
        return "home";
    }
}
