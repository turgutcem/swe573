package com.swe573.swe573.controller;

import com.swe573.swe573.model.Topic;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.SearchDTO;
import com.swe573.swe573.service.TopicService;
import com.swe573.swe573.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BrowseController {
    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public String topics(
                       Authentication authentication,
                       Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("searchObject",new SearchDTO());
        model.addAttribute("topicsfollowed",user.getFollowedTopics());
        model.addAttribute("topicsrecommend",topicService.topicRecommendation(user.getFollowedTopics()));

        return "topics";
    }

    @GetMapping("/topics/{topicName}")
    public String topicName(@PathVariable String topicName,
                            Authentication authentication,
                            Model model){

        User user=userService.findByEmail(authentication.getName()).get();
        if(!topicService.findTopicByName(topicName).isPresent()) return "redirect:/topics";
        Topic topic=topicService.findTopicByName(topicName).get();
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("searchObject",new SearchDTO());
        model.addAttribute("isfollowed",user.getFollowedTopics().contains(topic));
        model.addAttribute("followedby",topic.getFollowedBy().size());
        model.addAttribute("topic",topic);
        model.addAttribute("topicgibis",topicService.getTopicPageGibis(topic,user));

        return "topic";
    }

    @PostMapping("/unfollow")
    public String unfollow( @RequestBody String topic,
                            Authentication authentication){
        topic=topic.split("=")[1];
        User user=userService.findByEmail(authentication.getName()).get();
        topicService.unfollow(user,topicService.findTopicByName(topic).get());
        return "redirect:/topics/"+topic;
    }
    @PostMapping("/follow")
    public String follow( @RequestBody String topic,
                            Authentication authentication){
        topic=topic.split("=")[1];
        User user=userService.findByEmail(authentication.getName()).get();
        topicService.follow(user,topicService.findTopicByName(topic).get());
        return "redirect:/topics/"+topic;
    }

    @GetMapping("/users")
    public String users(@RequestParam(required = false) Long page,
                       Authentication authentication,
                       Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        model.addAttribute("searchObject",new SearchDTO());
        model.addAttribute("userrecomend",userService.recommend(user));
        model.addAttribute("usersfollowed",userService.getFriends(user));
        return "users";
    }
}
