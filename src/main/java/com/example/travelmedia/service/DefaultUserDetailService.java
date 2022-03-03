package com.example.travelmedia.service;

import com.example.travelmedia.model.User;
import com.example.travelmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserDetailService implements UserDetailsService {
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepository.findByMail(mail);
        if(user!=null){
            return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), user.getAuthorities());
        }
        throw new UsernameNotFoundException("Invalid email '"+mail+"' or password ");
    }
}
