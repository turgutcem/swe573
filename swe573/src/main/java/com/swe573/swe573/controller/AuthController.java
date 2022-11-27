package com.swe573.swe573.controller;

import com.swe573.swe573.model.dto.UserLoginDTO;
import com.swe573.swe573.model.dto.UserRegistrationDTO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller("/")
public class AuthController {

    @GetMapping("/login")
    public String login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect( "/");
            return null;
        }
        return "iglogin";
    }
    @GetMapping
    public String home(){
        return "home";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request,
                           HttpServletResponse response,
                           Model model) throws IOException {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect( "/");
            return null;
        }
        model.addAttribute("registrationDTO",new UserRegistrationDTO());
        return "registration";
    }

    @PostMapping("/register")
    public String registerform(@Valid @ModelAttribute("user") UserRegistrationDTO registrationDTO,
                               BindingResult bindingResult,
                               Model model){
        return "";
    }
}
