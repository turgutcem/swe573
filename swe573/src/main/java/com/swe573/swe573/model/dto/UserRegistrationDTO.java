package com.swe573.swe573.model.dto;

import com.swe573.swe573.config.passwordconfig.Password;
import lombok.Data;

@Data
public class UserRegistrationDTO {

    private String email;
    private String username;
    @Password
    private String password;
}
