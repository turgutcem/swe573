package com.swe573.swe573.controller;

import com.swe573.swe573.model.dto.UserRegistrationDTO;
import com.swe573.swe573.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegistrationRestController {

   /* private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        userService.registerUser(userRegistrationDTO);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }*/

}
