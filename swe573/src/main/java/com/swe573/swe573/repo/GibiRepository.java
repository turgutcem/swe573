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


}
