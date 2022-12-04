package com.swe573.swe573.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChangeUsernameDTO {

    @NotBlank(message = "Username can not be blank")
    @NotEmpty(message = "Username can not be empty")
    @NotNull
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
