package com.swe573.swe573.model.dto;

import com.swe573.swe573.config.passwordconfig.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRegistrationDTO {

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email can not be blank")
    @NotEmpty(message = "Email can not be empty")
    @NotNull
    private String email;
    @NotBlank(message = "Username can not be blank")
    @NotEmpty(message = "Username can not be empty")
    @NotNull
    private String username;
    @Password
    @NotBlank(message = "Password can not be blank")
    @NotEmpty(message = "Password can not be empty")
    @NotNull
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
