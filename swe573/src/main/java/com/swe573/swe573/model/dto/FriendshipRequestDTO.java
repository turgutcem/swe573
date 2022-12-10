package com.swe573.swe573.model.dto;

import com.swe573.swe573.model.User;

public class FriendshipRequestDTO {

    private String sender;
    private String receiver;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
