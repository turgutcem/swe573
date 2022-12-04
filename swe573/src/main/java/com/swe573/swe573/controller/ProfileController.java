package com.swe573.swe573.controller;

import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.ChangePasswordDTO;
import com.swe573.swe573.model.dto.ChangeUsernameDTO;
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
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(@RequestParam(required = false) String username,
                          Authentication authentication,
                          Model model){
        if(username==null){
            User user=userService.findByEmail(authentication.getName()).get();
            model.addAttribute("iguser",user);
            return "profile";
        }
        else {
            return "redirect:/";
        }

    }

    @GetMapping("/settings")
    public String settings(Authentication authentication,
                           Model model,
                           @RequestParam(required = false) boolean usernamechange,
                           @RequestParam(required = false) boolean passwordchange
                           ){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("changeUsernameDTO",new ChangeUsernameDTO());
        model.addAttribute("changePasswordDTO",new ChangePasswordDTO());
        if(usernamechange) model.addAttribute("usernamechange", true);
        if(passwordchange) model.addAttribute("passwordchange",true);

        return "settings";
    }




    @PostMapping("/changepassword")
    public String changePassword(@Valid @ModelAttribute("changePasswordDTO") ChangePasswordDTO changePasswordDTO,
                                 BindingResult bindingResult,
                                 Authentication authentication,
                                 Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("changePasswordDTO",changePasswordDTO);
            model.addAttribute("changeUsernameDTO",new ChangeUsernameDTO());
            model.addAttribute("iguser",userService.findByEmail(authentication.getName()).get());
            return "settings";
        }
        User user=userService.findByEmail(authentication.getName()).get();
        userService.changePassword(changePasswordDTO,user);
        return "redirect:/settings?passwordchange=true";
    }


    @PostMapping("/changeusername")
    public String changeUsername(@Valid @ModelAttribute("changeUsernameDTO") ChangeUsernameDTO changeUsernameDTO,
                                 BindingResult bindingResult,
                                 Authentication authentication,
                                 Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("changeUsernameDTO",changeUsernameDTO);
            model.addAttribute("changePasswordDTO",new ChangePasswordDTO());
            model.addAttribute("iguser",userService.findByEmail(authentication.getName()).get());
            return "settings";
        }
        if(userService.findByUsername(changeUsernameDTO.getUsername()).isPresent()){
            model.addAttribute("changeUsernameDTO",changeUsernameDTO);
            model.addAttribute("changePasswordDTO",new ChangePasswordDTO());
            model.addAttribute("iguser",userService.findByEmail(authentication.getName()).get());
            model.addAttribute("usernameexists",true);
            return "settings";
        }
        User user=userService.findByEmail(authentication.getName()).get();
        userService.changeUsername(changeUsernameDTO,user);
        return "redirect:/settings?usernamechange=true";
    }
}
