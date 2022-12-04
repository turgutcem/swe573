package com.swe573.swe573.controller;

import com.swe573.swe573.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TopicsController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public String getTopic(@RequestParam(required = true) Long page,
                           Authentication authentication,
                           Model model){
        return "";
    }
}
