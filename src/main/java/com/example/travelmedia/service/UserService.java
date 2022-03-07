package com.example.travelmedia.service;

import com.example.travelmedia.dto.RegistrationDto;
import com.example.travelmedia.model.User;
import com.example.travelmedia.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    @Qualifier("userRepository")
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    public boolean isAlreadyUsedMail(String mail){
        Optional<User> userOptional =userRepository.findByMail(mail);
        if (userOptional.isPresent())return true;
        return false;
    }
    public boolean saveRegistrationForm(RegistrationDto registrationDto){
        if(!registrationDto.getPassword().equals(registrationDto.getConfirm()))return false;
        log.info("userService : math password");
        userRepository.save(registrationDto.toUser(passwordEncoder));
        return true;
    }
    public User fetchUserByMail(String mail){
        Optional<User> userOptionalr = userRepository.findByMail(mail);
        User user=null;
        if(userOptionalr.isPresent()){
            user=userOptionalr.get();
        }
        return user;
    }
}
