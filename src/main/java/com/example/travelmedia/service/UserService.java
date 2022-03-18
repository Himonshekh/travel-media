package com.example.travelmedia.service;

import com.example.travelmedia.dto.RegistrationDto;
import com.example.travelmedia.mapper.UserMapper;
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

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isAlreadyUsedMail(String mail) {
        Optional<User> userOptional = userRepository.findByMail(mail);
        return userOptional.isPresent();
    }

    public boolean saveRegistrationForm(RegistrationDto registrationDto) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirm())) return false;
        log.info("userService : math password");
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userRepository.save(UserMapper.INSTANCE.registationToUser(registrationDto));
//        userRepository.save(registrationDto.toUser(passwordEncoder));
        return true;
    }

    public User fetchUserByMail(String mail) {
//        Optional<User> userOptional = userRepository.findByMail(mail);
//        User user = null;
        return userRepository.findByMail(mail).orElse(null);
//        if (userOptional.isPresent()) {
//            user = userOptional.get();
//        }
//        return user;
    }
}
