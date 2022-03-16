package com.example.travelmedia.controller;

import com.example.travelmedia.dto.PostDto;
import com.example.travelmedia.dto.PrivacyDto;
import com.example.travelmedia.model.Location;
import com.example.travelmedia.repository.UserRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/home")
public class HomeController {
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

    @GetMapping
    public String getHomePage(Model model, @AuthenticationPrincipal User user,@ModelAttribute PrivacyDto privacyDto,@ModelAttribute PostDto postDto){
        log.info("user :"+ user);
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
        log.info("home page postDto : "+postDto);
        return "home";
    }
    @GetMapping("/profile")
    public String GoProfilePage(@AuthenticationPrincipal User user,Model model){
        List<PostDto> postDtoList = postService.fetchForProfilePageByUser(user.getUsername());
        com.example.travelmedia.model.User user1 = userService.fetchUserByMail(user.getUsername());

        model.addAttribute(user1);
        model.addAttribute(postDtoList);
        log.info("profile page postDtoList: "+postDtoList);
        log.info("profile page user1:"+user1);
        return "profile";
    }
    @GetMapping("/friends")
    public String GoFriendPage(@AuthenticationPrincipal User user){
        log.info("friend get: ");
        return "friends";
    }

    @PostMapping("/search")
    public String GoSearchPage(String search,@AuthenticationPrincipal User user){
        log.info("search : "+search);

        return "search";
    }
}
