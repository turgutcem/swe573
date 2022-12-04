package com.swe573.swe573.model.dto;

import com.swe573.swe573.config.passwordconfig.Password;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class ChangePasswordDTO {

    @Password
    @NotBlank(message = "Password can not be blank")
    @NotEmpty(message = "Password can not be empty")
    @NotNull
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
