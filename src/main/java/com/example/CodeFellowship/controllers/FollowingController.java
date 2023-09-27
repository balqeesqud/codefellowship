package com.example.CodeFellowship.controllers;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import com.example.CodeFellowship.repostories.AppUserJPA;
import com.example.CodeFellowship.repostories.PostJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class FollowingController {

    @Autowired
    private AppUserJPA appUserJPA;
    @Autowired
    private PostJPA postJPA;



    @PostMapping("/follow/{id}")
    public RedirectView followUser(@PathVariable Long id, Principal principal){
        if(principal != null){
            ApplicationUser currentApplicationUser = appUserJPA.findByUsername(principal.getName());
            ApplicationUser wantFollowApplicationUser = appUserJPA.findById(id).orElseThrow();
            if(currentApplicationUser != null && wantFollowApplicationUser != null){
                wantFollowApplicationUser.getFollowers().add(currentApplicationUser);
                appUserJPA.save(wantFollowApplicationUser);
            }
        }
        // Redirect to the user's profile page after the follow operation
        return new RedirectView("/users/" + id);
    }

    @DeleteMapping("/unfollow/{id}")
    public RedirectView unfollowUser(@PathVariable Long id, Principal principal) {
        // Retrieve the currently authenticated user
        ApplicationUser currentApplicationUser = appUserJPA.findById(id).orElseThrow();

        // Retrieve the user to unfollow based on the provided ID
        ApplicationUser userToUnfollow = appUserJPA.findById(id).orElseThrow();

        // Remove the user to unfollow from the current user's following list
        if(currentApplicationUser != null && userToUnfollow != null){
            userToUnfollow.getFollowers().remove(currentApplicationUser);
            appUserJPA.save(currentApplicationUser);
        }



        // Redirect to the user's profile page after the unfollow operation
        return new RedirectView("/users/" + id);
    }



    @GetMapping("/feed")
    public String getAllFeed(Principal p , Model m){
        if (p != null)
        {
            ApplicationUser myUser = appUserJPA.findByUsername(p.getName());
            Set<ApplicationUser> followed = myUser.getFollowing();
            followed.remove(myUser);
            List<Post> posts = postJPA.findAllByApplicationUserIn(followed);
            m.addAttribute("posts", posts);
        }

        return "feed";
    }
}