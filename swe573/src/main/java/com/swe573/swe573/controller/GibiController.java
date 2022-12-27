package com.swe573.swe573.controller;

import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.PostCommentDTO;
import com.swe573.swe573.model.dto.SearchDTO;
import com.swe573.swe573.model.dto.UpdateGibiDTO;
import com.swe573.swe573.service.CommentService;
import com.swe573.swe573.service.GibiService;
import com.swe573.swe573.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class GibiController {

    @Autowired
    private GibiService gibiService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @GetMapping("/gibis/{id}")
    public String getgibi(@PathVariable(required = true) Long id,
                          Authentication authentication,
                          Model model){
        Optional<Gibi> gibi=gibiService.findById(id);
        if(!gibi.isPresent()){
            return "redirect:/";
        }
        User user=userService.findByEmail(authentication.getName()).get();
        if(!gibiService.isPermittedToView(user,gibi.get())) return "redirect:/";
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("searchObject",new SearchDTO());
        model.addAttribute("gibi",gibi.get());
        model.addAttribute("postCommentDTO",new PostCommentDTO());
        if(gibi.get().getCreatedBy().equals(user))
            model.addAttribute("editable",true);
        else
            model.addAttribute("editable",false);
        model.addAttribute("comments",gibi.get().getComments());

        return "gibi";

    }

    @GetMapping("gibis/{id}/bookmark")
    public String bookmark(@PathVariable(required = true)Long id,
                           Authentication authentication){
        User user=userService.findByEmail(authentication.getName()).get();
        gibiService.bookmark(user,id);
        return "redirect:/gibis/"+id;
    }

    @GetMapping("gibis/{id}/updategibi")
    public String updateGibi(@PathVariable(required = true) Long id,
                             Authentication authentication,
                             Model model){

        User user=userService.findByEmail(authentication.getName()).get();

        if(!gibiService.findById(id).isPresent()) return "redirect:/";
        Gibi gibi=gibiService.findById(id).get();
        if(!gibiService.isPermittedToView(user,gibi)) return "redirect:/";
        if(!gibi.getCreatedBy().equals(user)){return "redirect:/gibis/"+id;}
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("searchObject",new SearchDTO());
        model.addAttribute("gibi",gibi);
        model.addAttribute("updateGibiDTO",new UpdateGibiDTO());

        return "updategibi";
    }

    @PostMapping("gibis/{id}/updategibi")
    public String updateGibi(@PathVariable(required = true) Long id,
                             @Valid @ModelAttribute("gibi") UpdateGibiDTO updateGibiDTO,
                             BindingResult bindingResult,
                             Authentication authentication){
        User user=userService.findByEmail(authentication.getName()).get();
        if(bindingResult.hasErrors()){
            return "redirect:/gibis/"+id;
        }
        updateGibiDTO.setId(id);
        gibiService.updateGibi(user,updateGibiDTO);
        return "redirect:/gibis/"+id;
    }

    @GetMapping("gibis/{id}/delete")
    public String deleteGibi(@PathVariable(required = true) Long id,
                             Authentication authentication){
        gibiService.deleteGibi(id);
        return "redirect:/";
    }

    @PostMapping("gibis/{id}")
    public String postComment(@PathVariable(required = true) Long id,
                              @Valid @ModelAttribute("postCommentDTO") PostCommentDTO postCommentDTO,
                              BindingResult bindingResult,
                              Authentication authentication
                              ){
        if(bindingResult.hasErrors()){
            return "redirect:/gibis/"+id;
        }
        User user=userService.findByEmail(authentication.getName()).get();
        postCommentDTO.setGibiId(id);
        gibiService.addComment(user,postCommentDTO);

        return"redirect:/gibis/"+id;

    }
}
