package com.example.travelmedia.controller;

import com.example.travelmedia.dto.RegistrationDto;
import com.example.travelmedia.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/auth/")
public class AuthController {
    @Autowired
    @Qualifier("userService")
    UserService userService;

    @ModelAttribute(name="registrationDto")
    public RegistrationDto registrationDto(){
        return new RegistrationDto();
    }

    @GetMapping("/register")
    public String getRegistrationForm(){
        log.info("in get register controller...");
        return "registration";
    }
    @PostMapping("/register")
    public String postRegistrationForm(@Valid RegistrationDto registrationDto, Errors errors){
        log.info("in post register controller...");
        log.info("registration form : "+registrationDto);
        if(errors.hasErrors()){
            return "registration";
        }
        if(!userService.saveRegistrationForm(registrationDto)){
//            errors.addError(new ObjectError("confirm","must match"));
            errors.addAllErrors((Errors) new ObjectError("match","errors"));
            if(errors.hasErrors())return "registration";

            log.info("no errorr");
            return "registration";
        }

        return "redirect:/login";
    }
}
