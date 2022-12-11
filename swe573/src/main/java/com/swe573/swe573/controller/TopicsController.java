package com.swe573.swe573.controller;

import com.swe573.swe573.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TopicsController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics/{topicName}")
    public String getTopic(@PathVariable(required = false) Optional<String> topicName,
                           @RequestParam(required = false) Integer page,
                           Authentication authentication,
                           Model model){
        if(!topicName.isPresent()){

        }
        else{

        }
        return "";
    }
}
