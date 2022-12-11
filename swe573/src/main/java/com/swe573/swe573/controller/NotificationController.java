package com.swe573.swe573.controller;

import com.swe573.swe573.model.User;
import com.swe573.swe573.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationController {

    @Autowired
    private UserService userService;

    @GetMapping("/notifications")
    public String notifications(Authentication authentication,
                                Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("notifications",user.getNotifications());
        return "notifications";
    }
}
