package com.swe573.swe573.service;

import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.Topic;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.GetGibiDTO;
import com.swe573.swe573.model.dto.PostGibiDTO;
import com.swe573.swe573.model.enums.GibiAccessLevel;
import com.swe573.swe573.repo.GibiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GibiService {

    @Autowired
    private GibiRepository gibiRepository;
    @Autowired
    private TopicService topicService;

    @Transactional
    public void saveGibi(User user, PostGibiDTO postGibiDTO){
        Gibi gibi=new Gibi();
        gibi.setCreatedBy(user);
        switch (postGibiDTO.getGibiAccessLevel()){
            case "PUBLIC":
                gibi.setAccessLevel(GibiAccessLevel.PUBLIC);
                break;
            case "DEFAULT":
                gibi.setAccessLevel(GibiAccessLevel.DEFAULT);
                break;
            case "PRIVATE":
                gibi.setAccessLevel(GibiAccessLevel.PRIVATE);
                break;
        }
        gibi.setURL(postGibiDTO.getURL());
        gibi.setOnComment(postGibiDTO.getOnComment());
        Optional<Topic> topic=topicService.findTopicByName(postGibiDTO.getTopic());
        if(topic.isPresent()){
            gibi.setTopic(topic.get());
        }
        else{
            Topic topic1=new Topic();
            topic1.setTopicName(postGibiDTO.getTopic());
            List<Gibi> gibiList=new ArrayList<>();
            gibiList.add(gibi);
            topic1.setGibis(gibiList);
            topicService.saveTopic(topic1);
            gibi.setTopic(topic1);
        }

        gibiRepository.save(gibi);

    }

    @Transactional
    public List<GetGibiDTO> getGibiDTOList(){
        List<Gibi> gibiList=gibiRepository.findAll();
        return getGibiDTOList(gibiList);
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
