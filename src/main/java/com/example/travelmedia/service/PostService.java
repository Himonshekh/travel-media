package com.example.travelmedia.service;

import com.example.travelmedia.dto.PostDto;
import com.example.travelmedia.model.Location;
import com.example.travelmedia.model.Post;
import com.example.travelmedia.repository.LocationRepository;
import com.example.travelmedia.repository.PostRepository;
import com.example.travelmedia.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostService {
    @Autowired
    @Qualifier("postRepository")
    PostRepository postRepository;
    @Autowired
    @Qualifier("userRepository")
    UserRepository userRepository;

    @Autowired
    @Qualifier("locationRepository")
    LocationRepository locationRepository;

    public PostDto fetchPostById(Long id){
        Optional<Post> postOptional = postRepository.findById(id);
        Post post =postOptional.get();

        return new PostDto(post.getId(),post.getUser(), post.getStatus(),post.getLocation().getName(),post.getPrivacy());
    }
    public List<PostDto> fetchForHomePage(String mail){
//        @AuthenticationPrincipal String mail;
        List<Post> posts = postRepository.findAll();
        List<PostDto>postDtoList = new ArrayList<>();
        Optional<com.example.travelmedia.model.User> userOptional = userRepository.findByMail(mail);
        com.example.travelmedia.model.User user = null;
        if(userOptional.isPresent()){
            user=userOptional.get();
        }else {
            return postDtoList;
        }
        Long id = user.getId();

//        posts.forEach();
        for (Post post:posts){
            if(post.getUser().getId()==id || post.getPrivacy().charAt(1)=='u'){
                postDtoList.add(new PostDto(post.getId(),post.getUser(), post.getStatus(),post.getLocation().getName(),post.getPrivacy()));
            }
        }
        return postDtoList;
    }
    public void updateThisPost(PostDto postDto,User user){
        Optional<Post> postOptional = postRepository.findById(postDto.getId());
        Post post = postOptional.get();
        post.setStatus(postDto.getStatus());
        post.setLocation(getLocation(postDto.getLocation()));
        post.setPrivacy(postDto.getPrivacy());
        postRepository.save(post);
    }
    public void saveThisPost(PostDto postDto,User user){
        Post post= new Post(toUser(user),postDto.getStatus(),getLocation(postDto.getLocation()),postDto.getPrivacy());
        postRepository.save(post);
        log.info("post save successfully: "+post);
    }
    public com.example.travelmedia.model.User toUser(User user){
        Optional<com.example.travelmedia.model.User> userOptional = userRepository.findByMail(user.getUsername());
        com.example.travelmedia.model.User user1 =null;
        if (userOptional.isPresent()){
            user1=userOptional.get();
        }
        return user1;
    }
    public Location getLocation(String location){
        Location location1 = locationRepository.findByName(location);
        return location1;
    }
}
