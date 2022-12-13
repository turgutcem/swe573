package com.swe573.swe573.repo;

import com.swe573.swe573.model.Topic;
import com.swe573.swe573.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TopicsRepository extends JpaRepository<Topic,Long> {

    Optional<Topic> findByTopicName(String topicName);

    List<Topic> findByTopicNameNotIn(List<String> topicNames);

    long countByFollowedBy(User followedBy);


}
