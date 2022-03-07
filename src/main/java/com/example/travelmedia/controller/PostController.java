package com.example.travelmedia.controller;

import com.example.travelmedia.dto.CustomError;
import com.example.travelmedia.dto.PostDto;
import com.example.travelmedia.dto.PrivacyDto;
import com.example.travelmedia.dto.RegistrationDto;
import com.example.travelmedia.model.Location;
import com.example.travelmedia.model.Post;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
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

    @ModelAttribute(name="customError")
    public CustomError customError(){
        return new CustomError();
    }

    @ModelAttribute("privacyDto")
    public PrivacyDto privacyDto(){
        return new PrivacyDto();
    }


    @PostMapping("/create")
    public String createPost(@Valid PostDto postDto, Errors errors, @AuthenticationPrincipal User user,Model model,@ModelAttribute PrivacyDto privacyDto){
        log.info("postDto: "+postDto);
        if(errors.hasErrors()){
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
    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model,@ModelAttribute PrivacyDto privacyDto){
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
    public String editSubmitPost(@Valid PostDto postDto, Errors errors, @AuthenticationPrincipal User user, Model model, @ModelAttribute PrivacyDto privacyDto,@ModelAttribute CustomError customError){
        log.info("editsubmitpost:::::::::::: : "+postDto);
        if(errors.hasErrors()){
            log.info("errrroooooorrrr : "+postDto.getId());
            postDto=postService.fetchPostById(postDto.getId());
            List<Location> locationList = locationService.fetchAllLocation();
            customError.customErrors.put("status","status not empty");
            log.info("edit fetch post : "+postDto);
            privacyDto.setPrivacies(Arrays.asList("public","private"));
            model.addAttribute(customError);
            model.addAttribute(postDto);
            model.addAttribute(privacyDto);
            model.addAttribute(locationList);
            return "edit";
        }
        log.info("edit postDto: "+postDto);
        postService.updateThisPost(postDto,user);
        log.info("update success full");
        return "redirect:/home";
    }
    @GetMapping("/pined/{id}")
    public String pinedPost(@PathVariable Long id,@AuthenticationPrincipal User user){
        log.info("pinned post id : "+id);
        postService.updatePostByPin(id,user.getUsername());
        return "redirect:/home/profile";
    }
    @GetMapping("/delete/{id}")
    @Transactional
    public String deletePost(@PathVariable Long id){
        log.info("delete this post :" +id);
        postService.deletePostById(id);
        return "redirect:/home/profile";
    }
}
