package com.example.CodeFellowship.repostories;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJPA extends JpaRepository<Post, Long> {
    List<Post> findByUserId(ApplicationUser user);
}
