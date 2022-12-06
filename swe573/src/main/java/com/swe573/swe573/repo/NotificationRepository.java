package com.swe573.swe573.repo;

import com.swe573.swe573.model.Notification;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.enums.NotificationType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>{
    List<Notification> findByNotifiedUser(User notifiedUser);

    List<Notification> findByNotifiedUserAndNotificationTypeOrderByCreateDateDesc
            (User notifiedUser, NotificationType notificationType, Sort sort);

}
