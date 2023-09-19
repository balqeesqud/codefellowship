package com.example.CodeFellowship.repostories;

import com.example.CodeFellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJPA extends JpaRepository<Post, Long> {
}
