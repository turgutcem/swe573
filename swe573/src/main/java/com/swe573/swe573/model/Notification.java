package com.swe573.swe573.model;

import com.swe573.swe573.model.enums.NotificationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@RequiredArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private Long id;

    @CreationTimestamp
    @Column(name = "createDate")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "notification_by_user_id",nullable = false)
    private User notifiedUser;

    @ManyToOne
    @JoinColumn(name = "notifier_by_user_id",nullable = false)
    private User notifierUser;

    @Column(name="notificationType",nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name="notificationMessage",nullable = false)
    private String notificationMessage;

    @Column(name="status")
    private Boolean status=false;


}
