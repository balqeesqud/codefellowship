package com.example.CodeFellowship.controllers;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import com.example.CodeFellowship.repostories.AppUserJPA;
import com.example.CodeFellowship.repostories.PostJPA;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class PostController {

    private AppUserJPA appUserJPA;
    private PostJPA postJPA;

    public PostController(AppUserJPA appUserJPA, PostJPA postJPA) {
        this.appUserJPA = appUserJPA;
        this.postJPA = postJPA;
    }

    @PostMapping("/create-post")
    public RedirectView addPost(Principal p , String body) {

        if (p != null && p.getName() != null) {
            String username = p.getName();
            ApplicationUser appUser = appUserJPA.findByUsername(username);

            if (appUser != null) {
                Post post = new Post(body, appUser, LocalDate.now()); // Use LocalDate.now() for createdAt
                post.setApplicationUser(appUser);
                post.setCreatedAt(LocalDate.now());

                postJPA.save(post);
            }
        }

        return new RedirectView("/myposts");
    }



}