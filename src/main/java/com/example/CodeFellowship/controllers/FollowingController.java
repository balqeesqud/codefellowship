package com.example.CodeFellowship.controllers;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import com.example.CodeFellowship.repostories.AppUserJPA;
import com.example.CodeFellowship.repostories.PostJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

//    @PostMapping("/follow/{userId}")
//    public RedirectView followUser(@PathVariable Long userId, Principal p) {
//        boolean isFollowing = false;
//        if (p != null) {
//            String username = p.getName();
//            ApplicationUser loggedInUser = appUserJPA.findByUsername(username);
//            ApplicationUser userToFollow = appUserJPA.findByFollowingId(userId);
//
//            if (userToFollow != null) {
//                isFollowing = loggedInUser.getFollowing().stream()
//                        .anyMatch(user -> user.getUsername().equals(userToFollow.getUsername()));
//            }
//
//        }
//        return new RedirectView("/users/" + userId);
//    }

//    @PostMapping("/follow/{userId}")
//    public RedirectView followUser(@PathVariable Long userId, Principal p, Model model) {
//        if (p != null) {
//            String username = p.getName();
//            ApplicationUser loggedInUser = appUserJPA.findByUsername(username);
//
//            // Retrieve the user to follow based on userId
//            ApplicationUser userToFollow = appUserJPA.findById(userId)
//                    .orElseThrow(() -> new AppUserController.ResourceNotFoundException("User not found with id: " + userId));
//
//            // Check if the logged-in user is already following the user with userId
//            Set<ApplicationUser> followingUsers = appUserJPA.findByFollowingId(userId);
//            boolean isFollowing = followingUsers.contains(loggedInUser);
//
//            if (!isFollowing) {
//                // If not already following, add the user to the following set
//                loggedInUser.getFollowing().add(userToFollow);
//                userToFollow.getFollowers().add(loggedInUser); // Update the other side of the relationship
//                appUserJPA.save(loggedInUser);
//                appUserJPA.save(userToFollow); // Save the user being followed
//                model.addAttribute("followSuccess", true);
//            }
//        }
//
//        return new RedirectView("/users/{userId}");
//    }

//    @PostMapping("/follow/{userId}")
//    public RedirectView followUser(@PathVariable Long userId, Principal p, Model model) {
//        if (p != null) {
//            String username = p.getName();
//            ApplicationUser loggedInUser = appUserJPA.findByUsername(username);
//
//            // Retrieve the user to follow based on userId
//            ApplicationUser userToFollow = appUserJPA.findById(userId)
//                    .orElseThrow(() -> new AppUserController.ResourceNotFoundException("User not found with id: " + userId));
//
//            // Check if the logged-in user is already following the user with userId
//            boolean isFollowing = loggedInUser.getFollowing().contains(userToFollow);
//
//            if (!isFollowing) {
//                // If not already following, add the user to the following set
//                loggedInUser.getFollowing().add(userToFollow);
//                userToFollow.getFollowers().add(loggedInUser); // Update the other side of the relationship
//                appUserJPA.save(loggedInUser);
//                appUserJPA.save(userToFollow); // Save the user being followed
//
//                model.addAttribute("followSuccess", true);
//            }
//        }
//
//        return new RedirectView("/users/" + userId);
//    }


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