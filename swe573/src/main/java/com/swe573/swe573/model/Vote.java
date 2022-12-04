package com.swe573.swe573.model;

import com.swe573.swe573.model.enums.VoteType;
import com.swe573.swe573.model.enums.VoteTypeConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="votes")
@Getter
@Setter
@RequiredArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private Long id;

    @Column(name = "userID",nullable = false)
    private Long userID;

    @Column(name="gibiID",nullable = false)
    private Long gibiID;

    @Convert(converter= VoteTypeConverter.class)
    @Column(name = "voteType",nullable = false)
    private VoteType voteType;

}
