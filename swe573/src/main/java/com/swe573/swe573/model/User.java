package com.swe573.swe573.model;

import com.swe573.swe573.config.passwordconfig.Password;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private Long id;

    @Column(name="email",unique = true,nullable = false)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email can not be blank")
    @NotEmpty(message = "Email can not be empty")
    private String email;

    @Column(name = "username",unique = true,nullable = false)
    @NotBlank(message = "Username can not be blank")
    @NotEmpty(message = "Username can not be empty")
    private String username;

    @Column(name = "password",nullable = false)
    @NotBlank(message = "Password can not be blank")
    @NotEmpty(message = "Password can not be empty")
    private String password;

    @CreationTimestamp
    @Column(name = "createDate")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<User> friends = new ArrayList<>();

    @ManyToMany(mappedBy = "friends",fetch = FetchType.LAZY)
    private List<User> befriended = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Gibi> userGibis=new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Comment> userComments=new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "followedBy")
    private List<Topic> followedTopics=new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("createDate DESC")
    private List<Notification> notifications;



}
