package com.example.travelmedia.controller;

import com.example.travelmedia.dto.CustomError;
import com.example.travelmedia.dto.RegistrationDto;
import com.example.travelmedia.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
    @ModelAttribute(name="customError")
    public CustomError customError(){
        return new CustomError();
    }

    @GetMapping("/register")
    public String getRegistrationForm(){
        log.info("in get register controller...");
        return "registration";
    }
    @PostMapping("/register")
    public String postRegistrationForm(@Valid @ModelAttribute RegistrationDto registrationDto, Errors errors, Model model,@ModelAttribute CustomError customError){
        log.info("in post register controller...");
        log.info("registration form : "+registrationDto);
        if(errors.hasErrors()){
            return "registration";
        }
        log.info("no has error inside auth...");
        if(userService.isAlreadyUsedMail(registrationDto.getMail())){
            log.info("already mail found error");
            customError.customErrors.put("mail","Already used");
            model.addAttribute(customError);
            /// set already input text??
            return "registration";
        }
        if(!userService.saveRegistrationForm(registrationDto)){
            log.info("error saving way....");
            customError.customErrors.put("confirm","confirm error");
            model.addAttribute(customError);
            log.info("after set er  ror....");
            return "registration";
        }

        return "redirect:/login";
    }
}
