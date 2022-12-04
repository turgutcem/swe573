package com.swe573.swe573.service;

import com.swe573.swe573.model.User;
import com.swe573.swe573.model.dto.ChangePasswordDTO;
import com.swe573.swe573.model.dto.ChangeUsernameDTO;
import com.swe573.swe573.model.dto.UserRegistrationDTO;
import com.swe573.swe573.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper mapper=new ModelMapper();
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserRegistrationDTO registrationDTO){
        User user=mapper.map(registrationDTO, User.class);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void renewPassword(String email){
        User user = userRepository.findByEmail(email).get();
        PasswordGenerator passwordGenerator=new PasswordGenerator();
        String password = passwordGenerator.generatePassword(10,Arrays.asList(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        ));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        String emailBody = mailService.buildPasswordReminderEmail(user.getUsername(),password);
        mailService.sendEmail(user.getEmail(),"New Pass",emailBody);
    }
    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDTO,User user){
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void changeUsername(ChangeUsernameDTO changeUsernameDTO,User user){
        user.setUsername(changeUsernameDTO.getUsername());
        userRepository.save(user);
    }
}
