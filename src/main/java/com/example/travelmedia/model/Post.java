package com.example.travelmedia.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
//@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @NotNull
    @Size(min = 2 ,message = "status size minimum 2")
    private String status;
    @ManyToOne
    private Location location;
    @NotNull
    private String privacy;
    public Post(User user,String status,Location location,String privacy){
        this.user=user;
        this.status=status;
        this.location=location;
        this.privacy=privacy;
    }
}
