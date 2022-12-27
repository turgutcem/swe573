package com.swe573.swe573.controller;

import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.PostGibiDTO;
import com.swe573.swe573.model.dto.SearchDTO;
import com.swe573.swe573.service.GibiService;
import com.swe573.swe573.service.TopicService;
import com.swe573.swe573.service.UserService;
import com.swe573.swe573.service.search.IndexingService;
import com.swe573.swe573.service.search.SearchService;
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
import java.util.List;
import java.util.Map;

@Controller
public class TimelineController {


    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;
    @Autowired
    private GibiService gibiService;
    @Autowired
    private SearchService searchService;

    @Autowired
    private IndexingService indexingService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) Integer page,
                       Authentication authentication,
                       Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("searchObject",new SearchDTO());
        model.addAttribute("postGibiDTO",new PostGibiDTO());
        Map<String,Object> response=gibiService.getTimeline(page!=null?page:0,user);
        model.addAttribute("getGibiDTOList",response.get("gibis"));
        model.addAttribute("pagecount",response.get("pagecount"));
        model.addAttribute("currentpage",page!=null?page:0);
        return "home";
    }

    @PostMapping("/search")
    public String search(@Valid @ModelAttribute("searchObject") SearchDTO searchDTO,
                         BindingResult bindingResult,
                         Authentication authentication,
                         Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("searchObject",new SearchDTO());
        if(bindingResult.hasErrors()){
            return "redirect:/";
        }
        try{
            indexingService.initiateIndexing();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<User> userList = searchService.searchForUser(searchDTO.getSearchField());
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchResults(@RequestParam(required = false) Integer page,
                                Authentication authentication,
                                Model model){
        return "redirect:/";
    }

    @PostMapping("/postgibi")
    public String postgibi(@Valid @ModelAttribute("postGibiDTO") PostGibiDTO postGibiDTO,
                           BindingResult bindingResult,
                           Authentication authentication,
                           Model model){

        if(bindingResult.hasErrors()){
            return "redirect:/";
        }
        User user=userService.findByEmail(authentication.getName()).get();
        gibiService.saveGibi(user,postGibiDTO);
        return "redirect:/";
    }

}
