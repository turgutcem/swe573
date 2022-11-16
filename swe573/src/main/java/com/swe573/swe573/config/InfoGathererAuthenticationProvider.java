package com.swe573.swe573.config;

import com.swe573.swe573.model.User;
import com.swe573.swe573.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class InfoGathererAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String pwd=authentication.getCredentials().toString();
        Optional<User> user=userRepository.findByEmail(username);
        if(user.isPresent()){
            if(!user.get().getEnabled())
                throw new BadCredentialsException("You should verify your account,please check your email!");
            if(passwordEncoder.matches(pwd, user.get().getPassword())){
                return new UsernamePasswordAuthenticationToken(username,pwd,new ArrayList<>());
                }
            else
                throw new BadCredentialsException("Password not valid");
        }
        else
            throw new BadCredentialsException("No user registered with this details!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @PostConstruct
    public void createDummyUser(){
        User user=new User();
        user.setEmail("deneme@deneme.com");
        user.setUsername("deneme@deneme.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEnabled(true);
        userRepository.save(user);
        User user1=new User();
        user1.setUsername("email@email.com");
        user1.setEmail("email@email.com");
        user1.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user1);

    }

}
