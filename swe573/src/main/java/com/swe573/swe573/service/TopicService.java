package com.swe573.swe573.service;

import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.Topic;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.GetGibiDTO;
import com.swe573.swe573.model.enums.GibiAccessLevel;
import com.swe573.swe573.repo.GibiRepository;
import com.swe573.swe573.repo.TopicsRepository;
import com.swe573.swe573.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<Topic> topicRecommendation(List<Topic> followedTopics){
        if(followedTopics.size()!=0)
        return topicsRepository.findByTopicNameNotIn
                (followedTopics.stream().
                        map(Topic::getTopicName).collect(Collectors.toList()));
        else return topicsRepository.findAll();
    }

    @Transactional
    public List<GetGibiDTO> getTopicPageGibis(Topic topic, User user){

        return getGibiDTOList(gibiRepository.getTopicPage(topic,user,user.getFriends(), GibiAccessLevel.PUBLIC,GibiAccessLevel.DEFAULT));
    }
    @Transactional
    public Optional<Topic> findTopicByName(String name){
        return topicsRepository.findByTopicName(name);
    }

    @Transactional
    public void saveTopic(Topic topic){
        topicsRepository.save(topic);
    }

    @Transactional
    public void unfollow(User user,Topic topic){
        user.getFollowedTopics().remove(topic);
        topic.getFollowedBy().remove(user);
        userRepository.save(user);
        topicsRepository.save(topic);
    }

    @Transactional
    public void follow(User user,Topic topic){
        user.getFollowedTopics().add(topic);
        topic.getFollowedBy().add(user);
        userRepository.save(user);
        topicsRepository.save(topic);
    }

    private List<GetGibiDTO> getGibiDTOList(List<Gibi> gibiList){
        List<GetGibiDTO> getGibiDTOList=new ArrayList<>();
        for(Gibi gibi:gibiList){
            GetGibiDTO getGibiDTO=new GetGibiDTO();
            getGibiDTO.setId(gibi.getId());
            getGibiDTO.setCreatedBy(gibi.getCreatedBy().getUsername());
            getGibiDTO.setCreateDate(gibi.getCreateDate());
            getGibiDTO.setURL(gibi.getURL());
            getGibiDTO.setTopicName(gibi.getTopic().getTopicName());
            getGibiDTO.setOnComment(gibi.getOnComment());
            getGibiDTOList.add(getGibiDTO);
        }
        return getGibiDTOList;
    }

}
