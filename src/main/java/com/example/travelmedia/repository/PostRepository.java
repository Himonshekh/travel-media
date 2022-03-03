package com.example.travelmedia.repository;

import com.example.travelmedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
