package com.swe573.swe573.controller;

import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.User;
import com.swe573.swe573.service.GibiService;
import com.swe573.swe573.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class GibiController {

    @Autowired
    private GibiService gibiService;
    @Autowired
    private UserService userService;

    @GetMapping("/gibis")
    public String getgibi(@RequestParam(required = true) Long id,
                          Authentication authentication,
                          Model model){
        Optional<Gibi> gibi=gibiService.findById(id);
        if(!gibi.isPresent()){
            return "redirect:/";
        }

        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("gibi",gibi.get());
        model.addAttribute("comments",gibi.get().getComments());

        return "gibi";

    }
}
