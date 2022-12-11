package com.swe573.swe573.controller;

import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.ChangePasswordDTO;
import com.swe573.swe573.model.dto.ChangeUsernameDTO;
import com.swe573.swe573.model.dto.FriendshipRequestDTO;
import com.swe573.swe573.service.GibiService;
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

    @Autowired
    private GibiService gibiService;

    @GetMapping("/profile")
    public String profile(@RequestParam(required = false) Integer page,
                          @RequestParam(required = false) String username,
                          Authentication authentication,
                          Model model){
        if(username==null || username.equals(userService.findByEmail(authentication.getName()).get().getUsername())){
            User user=userService.findByEmail(authentication.getName()).get();
            model.addAttribute("iguser",user);
            model.addAttribute("friendcount",userService.friendCount(user));
            model.addAttribute("getGibiDTOList",gibiService.getMyProfile(user));
            model.addAttribute("self",true);
            return "profile";
        } else if (userService.findByUsername(username).isPresent()) {
            User user=userService.findByEmail(authentication.getName()).get();
            User user2=userService.findByUsername(username).get();
            model.addAttribute("self",false);
            model.addAttribute("friendcount",userService.friendCount(user));
            model.addAttribute("commonfriends",userService.friendsInCommon(user,user2));
            model.addAttribute("friendshipstatus",userService.friendshipStatus(user,user2));
            model.addAttribute("getGibiDTOList",gibiService.getMyProfile(user2));
            model.addAttribute("friendshiprequest",new FriendshipRequestDTO());
            model.addAttribute("iguser",user);
            model.addAttribute("iguser2",user2);
            return "profile";
        } else {
            return "redirect:/";
        }

    }

    @PostMapping("/friendshiprequest")
    public String friendshipRequest(@Valid @ModelAttribute("friendshiprequest") FriendshipRequestDTO friendshipRequestDTO,
                                    BindingResult bindingResult,
                                    Model model){
        userService.sendFriendshipRequest(userService.findByUsername(friendshipRequestDTO.getSender()).get(),userService.findByUsername(friendshipRequestDTO.getReceiver()).get());
        return "redirect:/profile?username="+friendshipRequestDTO.getReceiver();
    }

    @PostMapping("/acceptfriendship")
    public String acceptFriendship(@Valid @ModelAttribute("friendshiprequest") FriendshipRequestDTO friendshipRequestDTO,
                                   BindingResult bindingResult,
                                   Model model){
        userService.acceptFriendship(userService.findByUsername(friendshipRequestDTO.getSender()).get(),userService.findByUsername(friendshipRequestDTO.getReceiver()).get());
        return "redirect:/profile?username="+friendshipRequestDTO.getSender();
    }

    @PostMapping("/deletefriendship")
    public String deleteFriendship(@Valid @ModelAttribute("friendshiprequest") FriendshipRequestDTO friendshipRequestDTO,
                                   BindingResult bindingResult,
                                   Model model){
        User user1=userService.findByUsername(friendshipRequestDTO.getSender()).get();
        User user2=userService.findByUsername(friendshipRequestDTO.getReceiver()).get();
        userService.deleteFriend(user1,user2);
        return "redirect:/profile?username="+friendshipRequestDTO.getReceiver();
    }

    @GetMapping("/privategibis")
    public String getPrivateGibis(Authentication authentication,
                                  Model model,
                                  @RequestParam(required = false) Integer page)
    {
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("getGibiDTOList",gibiService.getPrivateGibis(page!=null?page:0,user));
        return "privategibis";
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
        model.addAttribute("friendcount",userService.friendCount(user));
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
            model.addAttribute("friendcount",userService.friendCount(userService.findByEmail(authentication.getName()).get()));
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
        User user=userService.findByEmail(authentication.getName()).get();
        if(bindingResult.hasErrors()){
            model.addAttribute("changeUsernameDTO",changeUsernameDTO);
            model.addAttribute("changePasswordDTO",new ChangePasswordDTO());
            model.addAttribute("iguser",userService.findByEmail(authentication.getName()).get());
            model.addAttribute("friendcount",userService.friendCount(user));
            return "settings";
        }
        if(userService.findByUsername(changeUsernameDTO.getUsername()).isPresent()){
            model.addAttribute("changeUsernameDTO",changeUsernameDTO);
            model.addAttribute("changePasswordDTO",new ChangePasswordDTO());
            model.addAttribute("iguser",userService.findByEmail(authentication.getName()).get());
            model.addAttribute("usernameexists",true);
            model.addAttribute("friendcount",userService.friendCount(user));
            return "settings";
        }

        userService.changeUsername(changeUsernameDTO,user);
        return "redirect:/settings?usernamechange=true";
    }
}
