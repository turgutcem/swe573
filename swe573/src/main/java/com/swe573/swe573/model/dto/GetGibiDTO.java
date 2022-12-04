package com.swe573.swe573.model.dto;

import java.time.LocalDateTime;

public class GetGibiDTO {

    private Long id;
    private String createdBy;
    private String topicName;
    private LocalDateTime createDate;
    private String URL;
    private String onComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getOnComment() {
        return onComment;
    }

    public void setOnComment(String onComment) {
        this.onComment = onComment;
    }
}
