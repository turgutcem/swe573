package com.swe573.swe573.controller;

import com.swe573.swe573.model.User;
import com.swe573.swe573.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TimelineController {


    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String home(@RequestParam(required = false) Long page,
                       Authentication authentication,
                       Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        return "home";
    }
}
