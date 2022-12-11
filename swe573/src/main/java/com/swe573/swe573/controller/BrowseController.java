package com.swe573.swe573.controller;

import com.swe573.swe573.model.Topic;
import com.swe573.swe573.model.User;
import com.swe573.swe573.service.TopicService;
import com.swe573.swe573.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("isfollowed",user.getFollowedTopics().contains(topic));
        model.addAttribute("followedby",topic.getFollowedBy().size());
        model.addAttribute("topicgibis",topicService.getTopicPageGibis(topic,user));

        return "topic";
    }

    @GetMapping("/users")
    public String users(@RequestParam(required = false) Long page,
                       Authentication authentication,
                       Model model){
        User user=userService.findByEmail(authentication.getName()).get();
        model.addAttribute("iguser",user);
        model.addAttribute("friendcount",userService.friendCount(user));
        return "users";
    }
}
