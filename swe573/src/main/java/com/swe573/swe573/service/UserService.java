package com.swe573.swe573.service;

import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.UserRegistrationDTO;
import com.swe573.swe573.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper mapper=new ModelMapper();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserRegistrationDTO registrationDTO){
        User user=mapper.map(registrationDTO, User.class);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userRepository.save(user);
    }
}
