package com.example.travelmedia.controller;

import com.example.travelmedia.dto.PostDto;
import com.example.travelmedia.dto.PrivacyDto;
import com.example.travelmedia.dto.RegistrationDto;
import com.example.travelmedia.model.Location;
import com.example.travelmedia.service.LocationService;
import com.example.travelmedia.service.PostService;
import com.example.travelmedia.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/post")
public class PostController {
    @Autowired
    @Qualifier("postService")
    PostService postService;
    @Autowired
    @Qualifier("locationService")
    LocationService locationService;

    @Autowired
    @Qualifier("userService")
    UserService userService;

    @ModelAttribute(name="postDto")
    public PostDto postDto(){
        return new PostDto();
    }

    @ModelAttribute("privacyDto")
    public PrivacyDto privacyDto(){
        return new PrivacyDto();
    }


    @PostMapping("/create")
    public String createPost(@Valid PostDto postDto, BindingResult bindingResult, @AuthenticationPrincipal User user,Model model,@ModelAttribute PrivacyDto privacyDto){
        log.info("postDto: "+postDto);
        if(bindingResult.hasErrors()){
            log.info("create : hasError");
            List<PostDto> postDtoList = postService.fetchForHomePage(user.getUsername());
//        postDtoList.add(new PostDto("himon","status done","dhaka","public"));postDtoList.add(new PostDto("asd","status asd","dhaasdka","public"));postDtoList.add(new PostDto("himasdon","status sd","asda","public"));

            log.info("postDtoList : "+postDtoList);
            privacyDto.setPrivacies(Arrays.asList("public","private"));

            List<Location>locationList = locationService.fetchAllLocation();
            com.example.travelmedia.model.User user1 = userService.fetchUserByMail(user.getUsername());
            log.info("locationList: "+locationList);
            model.addAttribute(postDtoList);
            model.addAttribute(privacyDto);
            model.addAttribute(locationList);
            model.addAttribute(user1);
            model.addAttribute(postDto);
            log.info("home page psotDto : "+postDto);
            return "home";
        }
        postService.saveThisPost(postDto,user);
        return "redirect:/home";
    }
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model,@ModelAttribute PrivacyDto privacyDto){
//        log.info("edit postDto : "+postDto);
        log.info("edit id : "+id);
        PostDto postDto=postService.fetchPostById(id);
        List<Location> locationList = locationService.fetchAllLocation();

        log.info("edit fetch post : "+postDto);
        privacyDto.setPrivacies(Arrays.asList("public","private"));
        model.addAttribute(postDto);
        model.addAttribute(privacyDto);
        model.addAttribute(locationList);
        return "edit";
    }
    @PostMapping("/edit")
    public String editSubmitPost(@Valid PostDto postDto, BindingResult bindingResult,Model model, @ModelAttribute PrivacyDto privacyDto){
        if(bindingResult.hasErrors()){
            log.info("errrroooooorrrr : "+postDto.getId());
            postDto=postService.fetchPostById(postDto.getId());
            List<Location> locationList = locationService.fetchAllLocation();

            log.info("edit fetch post : "+postDto);
            privacyDto.setPrivacies(Arrays.asList("public","private"));
            model.addAttribute(postDto);
            model.addAttribute(privacyDto);
            model.addAttribute(locationList);
            return "edit";
        }
        log.info("edit postDto: "+postDto);
//        postService.saveThisPost(postDto,user);
        return "redirect:/home";
    }
}
