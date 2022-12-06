package com.swe573.swe573.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostCommentDTO {


    private Long gibiId;
    @NotBlank(message = "Comment can not be blank")
    @NotEmpty(message = "Comment can not be empty")
    @NotNull
    private String comment;


    public Long getGibiId() {
        return gibiId;
    }

    public void setGibiId(Long gibiId) {
        this.gibiId = gibiId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
