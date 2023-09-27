package com.example.CodeFellowship.repostories;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PostJPA extends JpaRepository<Post, Long> {
    List<Post> findByApplicationUser(ApplicationUser user);

    List<Post> findAllByApplicationUserIn(Set<ApplicationUser> followed);
}