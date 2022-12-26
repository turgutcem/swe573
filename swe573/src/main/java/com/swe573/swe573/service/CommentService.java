package com.swe573.swe573.service;

import com.swe573.swe573.model.Comment;
import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.PostCommentDTO;
import com.swe573.swe573.repo.CommentRepository;
import com.swe573.swe573.repo.GibiRepository;
import com.swe573.swe573.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GibiRepository gibiRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void saveComment(User user, PostCommentDTO postCommentDTO){
        Comment comment = convertFromDTO(user,postCommentDTO);
        commentRepository.save(convertFromDTO(user,postCommentDTO));
        Gibi gibi=gibiRepository.findById(postCommentDTO.getGibiId()).get();
        notificationService.sendCommentNotification(user,gibi.getCreatedBy(),comment);
    }

    @Transactional
    public long getCommentCount(User user){
        return commentRepository.countByCreatedBy(user);
    }

    private Comment convertFromDTO(User user,PostCommentDTO postCommentDTO){
        Comment comment=new Comment();
        comment.setComment(postCommentDTO.getComment());
        comment.setCreatedBy(user);
        comment.setGibi(gibiRepository.findById(postCommentDTO.getGibiId()).get());
        return comment;

    }
}
