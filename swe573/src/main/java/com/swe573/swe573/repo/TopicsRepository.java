package com.swe573.swe573.repo;

import com.swe573.swe573.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TopicsRepository extends JpaRepository<Topic,Long> {

    Optional<Topic> findByTopicName(String topicName);
    List<Topic> findByIdNotIn(Collection<Long> ids);

}
