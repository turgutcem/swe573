package com.swe573.swe573.service;

import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.Topic;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.enums.GibiAccessLevel;
import com.swe573.swe573.repo.GibiRepository;
import com.swe573.swe573.repo.TopicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService
{

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private GibiRepository gibiRepository;

    @Transactional
    public List<Topic> topicRecommendation(List<Topic> followedTopics){
        return topicsRepository.findByIdNotIn(followedTopics.stream()
                .map(Topic::getId).collect(Collectors.toList()));
    }

    @Transactional
    public List<Gibi> getTopicPageGibis(Topic topic, User user){
        return gibiRepository.getTopicPage(topic,user.getFriends(), GibiAccessLevel.PUBLIC,GibiAccessLevel.DEFAULT);
    }
    @Transactional
    public Optional<Topic> findTopicByName(String name){
        return topicsRepository.findByTopicName(name);
    }

    @Transactional
    public void saveTopic(Topic topic){
        topicsRepository.save(topic);
    }

}
