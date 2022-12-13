package com.swe573.swe573.repo;

import com.swe573.swe573.model.Comment;
import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByGibi(Gibi gibi);

    long countByCreatedBy(User createdBy);

}
