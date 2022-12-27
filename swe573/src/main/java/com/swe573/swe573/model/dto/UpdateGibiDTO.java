package com.swe573.swe573.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateGibiDTO
{
    private Long id;

    @NotBlank(message = "Access Level can not be blank")
    @NotEmpty(message = "Access Level can not be empty")
    @NotNull
    private String gibiAccessLevel;
    @NotBlank(message = "URL can not be blank")
    @NotEmpty(message = "URL can not be empty")
    @NotNull
    private String URL;
    @NotBlank(message = "Comment can not be blank")
    @NotEmpty(message = "Comment can not be empty")
    @NotNull
    private String onComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getGibiAccessLevel() {
        return gibiAccessLevel;
    }

    public void setGibiAccessLevel(String gibiAccessLevel) {
        this.gibiAccessLevel = gibiAccessLevel;
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
