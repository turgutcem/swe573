package com.swe573.swe573.service;

import com.swe573.swe573.model.Comment;
import com.swe573.swe573.model.Notification;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.enums.NotificationType;
import com.swe573.swe573.repo.NotificationRepository;
import com.swe573.swe573.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Async
    @Transactional
    public void sendFriendshipNotification(User sender, User receiver){
        Notification notification=new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setNotificationType(NotificationType.FRIENDSHIP);
        notification.setNotificationMessage(sender.getUsername()+" sent you a friendship request");

        notificationRepository.save(notification);

        receiver.getNotifications().add(notification);
        userRepository.save(receiver);
    }

    @Async
    @Transactional
    public void sendCommentNotification(User sender, User receiver, Comment comment){
        Notification notification=new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setNotificationType(NotificationType.COMMENT);
        notification.setNotificationMessage(sender.getUsername()+" commented on your gibi: "+comment.getComment());
        notification.setGibiID(comment.getGibi().getId());

        notificationRepository.save(notification);

        receiver.getNotifications().add(notification);
        userRepository.save(receiver);
    }
}
