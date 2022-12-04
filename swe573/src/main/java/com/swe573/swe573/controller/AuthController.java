package com.swe573.swe573.controller;

import com.swe573.swe573.model.dto.ForgotPasswordDTO;
import com.swe573.swe573.model.dto.UserRegistrationDTO;
import com.swe573.swe573.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(required = false)boolean registersuccess,
                        @RequestParam(required = false)boolean sendpasswordmail,
                        Model model) throws IOException {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect( "/");
            return null;
        }
        model.addAttribute("registrationsuccess",registersuccess);
        model.addAttribute("sendpasswordmail",sendpasswordmail);
        return "iglogin";
    }

    @GetMapping("/forgotpassword")
    public String forgotPassword(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Model model,
                                 @RequestParam(required = false)boolean usernotexist) throws IOException {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect( "/");
            return null;
        }
        model.addAttribute("usernotexist",usernotexist);
        model.addAttribute("forgotPasswordDTO",new ForgotPasswordDTO());
        return "forgotpassword";
    }
    @PostMapping("/forgotpassword")
    public String forgotPasswordPost(@Valid @ModelAttribute("registrationDTO") ForgotPasswordDTO forgotPasswordDTO,
                                     BindingResult bindingResult,
                                     Model model) throws IOException {

        if(!userService.findByEmail(forgotPasswordDTO.getEmail()).isPresent()){
            model.addAttribute("usernotexist",true);
            model.addAttribute("forgotPasswordDTO",forgotPasswordDTO);
            return "redirect:/forgotpassword?usernotexist=true";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("forgotPasswordDTO",forgotPasswordDTO);
            return "forgotpassword";
        }
        userService.renewPassword(forgotPasswordDTO.getEmail());
        return "redirect:/login?sendpasswordmail=true";
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
    public String registerform(@Valid @ModelAttribute("registrationDTO") UserRegistrationDTO registrationDTO,
                               BindingResult bindingResult,
                               Model model){
        if(userService.findByEmail(registrationDTO.getEmail()).isPresent()){
            model.addAttribute("registrationEmailError",true);
            model.addAttribute("registrationDTO",registrationDTO);
            return "registration";
        }
        if(userService.findByUsername(registrationDTO.getUsername()).isPresent()){
            model.addAttribute("registrationUsernameError",true);
            model.addAttribute("registrationDTO",registrationDTO);
            return "registration";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationDTO",registrationDTO);
            return "registration";
        }

        userService.registerUser(registrationDTO);
        return "redirect:/login?registersuccess=true";
    }
}
