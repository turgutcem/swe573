package com.swe573.swe573.service;

import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.Topic;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.GetGibiDTO;
import com.swe573.swe573.model.dto.PostCommentDTO;
import com.swe573.swe573.model.dto.PostGibiDTO;
import com.swe573.swe573.model.dto.UpdateGibiDTO;
import com.swe573.swe573.model.enums.GibiAccessLevel;
import com.swe573.swe573.repo.GibiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class GibiService {

    @Autowired
    private GibiRepository gibiRepository;
    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;


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
        String topicName= postGibiDTO.getTopic().replaceAll("\\s", "").toLowerCase();
        Optional<Topic> topic=topicService.findTopicByName(topicName);
        if(topic.isPresent()){
            gibi.setTopic(topic.get());
            userService.followTopic(user,topic.get());
        }
        else{
            Topic topic1=new Topic();
            topic1.setTopicName(topicName);
            List<Gibi> gibiList=new ArrayList<>();
            gibiList.add(gibi);
            topic1.setGibis(gibiList);
            topic1.getFollowedBy().add(user);
            topicService.saveTopic(topic1);
            gibi.setTopic(topic1);
            userService.followTopic(user,topic1);
        }

        gibiRepository.save(gibi);

    }

    @Transactional
    public void bookmark(User user,Long id){
        user.getBookmarks().add(id);
    }

    @Transactional
    public List<Gibi> getBookmarks(User user){
        List<Gibi> gibiList=new ArrayList<>();
        for(Long id:user.getBookmarks()){
            Optional<Gibi> gibi=gibiRepository.findById(id);
            gibi.ifPresent(gibiList::add);
        }
        return gibiList;
    }

    @Transactional
    public void updateGibi(User user,UpdateGibiDTO updateGibiDTO){
        Gibi gibi = gibiRepository.findById(updateGibiDTO.getId()).get();
        gibi.setURL(updateGibiDTO.getURL());
        gibi.setOnComment(updateGibiDTO.getOnComment());
        switch (updateGibiDTO.getGibiAccessLevel()){
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
        gibiRepository.save(gibi);
    }

    @Transactional
    public void deleteGibi(Long id){
        Gibi gibi=gibiRepository.findById(id).get();
        Topic topic=topicService.findTopicByName(gibi.getTopic().getTopicName()).get();
        topic.getGibis().remove(gibi);
        topicService.saveTopic(topic);
        gibi.getComments().clear();
        gibiRepository.deleteById(id);

    }

    @Transactional
    public List<GetGibiDTO> getMyProfile(User user){
        return getGibiDTOList(gibiRepository.getMyProfile(
                                                          user,GibiAccessLevel.PRIVATE));

    }

    public boolean isPermittedToView(User user,Gibi gibi){
        if(gibi.getCreatedBy().equals(user)) return true;
        if(gibi.getAccessLevel().equals(GibiAccessLevel.PUBLIC)) return true;
        else if(gibi.getAccessLevel().equals(GibiAccessLevel.PRIVATE)&&gibi.getCreatedBy().equals(user)) return true;
        else if(gibi.getAccessLevel().equals(GibiAccessLevel.PRIVATE)) return false;
        else {
            if(userService.getFriends(user).contains(gibi.getCreatedBy())) return true;
            else return false;
        }

    }
    @Transactional
    public Optional<Gibi> findById(Long id){
        return gibiRepository.findById(id);
    }

    @Transactional
    public Map<String, Object> getTimeline(Integer page, User user){
        Pageable pageable= PageRequest.of(page,10);
        Page<Gibi> timeLine=gibiRepository.getTimeline(pageable,
                        user,
                        user.getFriends(),
                        user.getBefriended(),
                        GibiAccessLevel.PRIVATE,
                        GibiAccessLevel.PUBLIC,
                        user.getFollowedTopics());
        int totalPages=timeLine.getTotalPages();
        List<GetGibiDTO> gibiDTOS=getGibiDTOList(timeLine.getContent());
        Map<String,Object> response=new HashMap<>();
        response.put("gibis",gibiDTOS);
        response.put("pagecount",totalPages);
        return response;
    }

    @Transactional
    public List<GetGibiDTO> getPrivateGibis(Integer page,User user){

        return getGibiDTOList(gibiRepository.findByCreatedByAndAccessLevelOrderByCreateDateDesc(user,GibiAccessLevel.PRIVATE));
    }

    @Transactional
    public void addComment(User user, PostCommentDTO commentDTO){
        commentService.saveComment(user,commentDTO);
    }

    @Transactional
    public long countByGibi(User user){
        return gibiRepository.countByCreatedBy(user);
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
