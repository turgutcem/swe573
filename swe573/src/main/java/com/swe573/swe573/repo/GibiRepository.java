package com.swe573.swe573.repo;

import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.Topic;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.enums.GibiAccessLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GibiRepository extends JpaRepository<Gibi,Long> {
    @Query("SELECT g FROM Gibi g WHERE " +
            "(g.createdBy IN :friends AND g.createdBy IN :beFriendedBy AND g.accessLevel <> :gibiAccessLevel) " +
            "OR (g.topic IN :followedTopics AND g.accessLevel = :topicAccessLevel)" +
            "OR (g.createdBy = :user AND g.accessLevel <> :gibiAccessLevel) " +
            "ORDER BY g.createDate DESC")
    Page<Gibi> getTimeline(Pageable pageable,
                           User user,
                           List<User> friends,
                           List<User> beFriendedBy,
                           GibiAccessLevel gibiAccessLevel,
                           GibiAccessLevel topicAccessLevel,
                           List<Topic> followedTopics);
    @Query("SELECT g FROM Gibi g WHERE " +
            "g.topic = :topic AND " +
            "(" +
            "((g.accessLevel = :friendGibiAccessLevel OR g.accessLevel = :topicGibiAccessLevel) AND g.createdBy = :user) OR " +
            "(g.accessLevel = :topicGibiAccessLevel) OR ((g.accessLevel = :friendGibiAccessLevel OR g.accessLevel = :topicGibiAccessLevel ) AND g.createdBy IN :friends) ) ORDER BY g.createDate DESC")
    List<Gibi> getTopicPage(Topic topic,User user,List<User> friends,GibiAccessLevel topicGibiAccessLevel,GibiAccessLevel friendGibiAccessLevel);
    @Query("SELECT g FROM Gibi g WHERE g.accessLevel <> :gibiAccessLevel " +
            "AND g.createdBy = :user ORDER BY g.createDate DESC")
    List<Gibi> getMyProfile(User user,GibiAccessLevel gibiAccessLevel);

    long countByCreatedBy(User createdBy);

    List<Gibi> findByCreatedByAndAccessLevelOrderByCreateDateDesc(User createdBy, GibiAccessLevel accessLevel);




}
