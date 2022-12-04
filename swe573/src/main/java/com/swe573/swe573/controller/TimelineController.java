package com.swe573.swe573.controller;

import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.PostGibiDTO;
import com.swe573.swe573.service.GibiService;
import com.swe573.swe573.service.TimelineService;
import com.swe573.swe573.service.TopicService;
import com.swe573.swe573.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class TimelineController {


    @Autowired
    private UserService userService;
    @Autowired
    private TimelineService timelineService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private GibiService gibiService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) Long page,
                       Authentication authentication,
                       Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("postGibiDTO",new PostGibiDTO());
        model.addAttribute("getGibiDTOList",gibiService.getGibiDTOList());

        return "home";
    }

    @PostMapping("/postgibi")
    public String postgibi(@Valid @ModelAttribute("postGibiDTO") PostGibiDTO postGibiDTO,
                           BindingResult bindingResult,
                           Authentication authentication,
                           Model model){

        if(bindingResult.hasErrors()){}
        User user=userService.findByEmail(authentication.getName()).get();
        gibiService.saveGibi(user,postGibiDTO);
        System.out.println("postgibi");
        return "";
    }

}
