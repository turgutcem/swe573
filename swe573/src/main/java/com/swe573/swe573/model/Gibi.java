package com.swe573.swe573.model;

import com.swe573.swe573.model.enums.GibiAccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "gibis")
@Getter
@Setter
@RequiredArgsConstructor
public class Gibi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_id",nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_by_id",nullable = false)
    private Topic topic;

    @CreationTimestamp
    @Column(name = "createDate")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @OneToMany(fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    @OrderBy("createDate ASC")
    private List<Comment> comments;

    @Column(name = "access_level")
    @Enumerated(EnumType.STRING)
    private GibiAccessLevel accessLevel;

    @Column(name = "url")
    private String URL;

    @Column(name = "onComment")
    private String onComment;

    @Column(name = "upVotes")
    private int upVotes=0;

    @Column(name = "downVotes")
    private int downVotes=0;

}
