package com.example.travelmedia.service;

import com.example.travelmedia.dto.RegistrationDto;
import com.example.travelmedia.dto.UserDto;
import com.example.travelmedia.model.User;
import com.example.travelmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    @Qualifier("userRepository")
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    public boolean saveRegistrationForm(RegistrationDto registrationDto){
        if(!registrationDto.getPassword().equals(registrationDto.getConfirm()))return false;
        userRepository.save(registrationDto.toUser(passwordEncoder));
        return true;
    }
    public User fetchUserByMail(String mail){
        User user = userRepository.findByMail(mail);
        return user;
    }
}
