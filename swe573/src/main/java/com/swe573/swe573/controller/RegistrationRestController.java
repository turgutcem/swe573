package com.swe573.swe573.controller;

import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.UserRegistrationDTO;
import com.swe573.swe573.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.reflect.Field;

@RestController
@RequiredArgsConstructor
public class RegistrationRestController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO){
        ModelMapper mapper=new ModelMapper();
        System.out.println(userRegistrationDTO.getEmail());
        userRepository.save(mapper.map(userRegistrationDTO, User.class));
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }
}
