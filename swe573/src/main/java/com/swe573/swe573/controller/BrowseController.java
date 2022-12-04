package com.swe573.swe573.controller;

import com.swe573.swe573.model.User;
import com.swe573.swe573.service.TopicService;
import com.swe573.swe573.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BrowseController {
    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/browse")
    public String browse(@RequestParam(required = false) Long page,
                         Authentication authentication,
                         Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        return "browse";
    }
    @GetMapping("/browse/topics")
    public String topics(@RequestParam(required = false) Long page,
                       Authentication authentication,
                       Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        return "topics";
    }

    @GetMapping("/browse/users")
    public String users(@RequestParam(required = false) Long page,
                       Authentication authentication,
                       Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        return "users";
    }
}
