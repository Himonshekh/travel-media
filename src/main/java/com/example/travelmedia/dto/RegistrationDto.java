package com.example.travelmedia.dto;

import com.example.travelmedia.model.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegistrationDto {
    @NotNull
    @Pattern(regexp="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message="Must be formatted : travel.com")
    private String mail;
    @NotNull
    @Size(min = 1,message = "password must be at least 5 characters long")
    private String password;
//    @Size(min = 1,message = "password must be at least 5 characters long")
    private String confirm;
    @NotNull
    @Size(min = 1,message = "minimum 1 length")
    private String username;

    public User toUser(PasswordEncoder passwordEncoder){
        return new User(mail,passwordEncoder.encode(password),username);
    }
}
