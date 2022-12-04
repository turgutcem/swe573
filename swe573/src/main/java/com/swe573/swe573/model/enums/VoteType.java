package com.swe573.swe573.model.enums;

public enum VoteType {
    UPVOTE(1),
    NONVOTE(0),
    DOWNVOTE(-1);

    private int  voteType;
    VoteType(int voteType) {
        this.voteType =voteType;
    }
}
