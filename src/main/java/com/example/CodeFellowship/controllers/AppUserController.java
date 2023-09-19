package com.example.CodeFellowship.controllers;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import com.example.CodeFellowship.repostories.AppUserJPA;
import com.example.CodeFellowship.repostories.PostJPA;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Controller
public class AppUserController {

    private AppUserJPA appUserJPA;
    private PostJPA postJPA;

    public AppUserController(AppUserJPA appUserJPA, PostJPA postJPA) {
        this.appUserJPA = appUserJPA;
        this.postJPA = postJPA;
    }

    @GetMapping("/myprofile")
    public String getUserInfo(Model model, Principal p) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser appUser = appUserJPA.findByUsername(username);

            model.addAttribute("createdDate",appUser.getLocalDate() );
            model.addAttribute("username", username);
            model.addAttribute("firstName", appUser.getFirstName());
            model.addAttribute("lastName", appUser.getLastName());
            model.addAttribute("dateOfBirth", appUser.getDateOfBirth());
            model.addAttribute("bio", appUser.getBio());
            model.addAttribute("codeFellowUserId", appUser.getId());

            List<Post> userPosts = postJPA.findByUserId(appUser);
            model.addAttribute("userPosts", userPosts);

        }

        return "userInformation.html";

    }

    @GetMapping("/users/{id}")
    public String getUserInfo(Model model, Principal p, @PathVariable Long id) {
        {
            if (p != null)
            {
                String username = p.getName();
                ApplicationUser appBrowsingUser = appUserJPA.findByUsername(username);


                model.addAttribute("username", username);
                model.addAttribute("createdDate", appBrowsingUser.getLocalDate());
                model.addAttribute("firstName", appBrowsingUser.getFirstName());
                model.addAttribute("lastName", appBrowsingUser.getLastName());
                model.addAttribute("dateOfBirth", appBrowsingUser.getDateOfBirth());
                model.addAttribute("bio", appBrowsingUser.getBio());
                model.addAttribute("profilePic", appBrowsingUser.getProfilePic());
            }

            ApplicationUser codeFellowUser = appUserJPA.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("user not found with id " + id));

            model.addAttribute("codeFellowCreatedDate", LocalDateTime.now());
            model.addAttribute("codeFellowUsername", codeFellowUser.getUsername());
            model.addAttribute("codeFellowFirstName", codeFellowUser.getFirstName());
            model.addAttribute("codeFellowLastName", codeFellowUser.getLastName());
            model.addAttribute("codeFellowDateOfBirth", codeFellowUser.getDateOfBirth());
            model.addAttribute("codeFellowBio", codeFellowUser.getBio());
            model.addAttribute("codeFellowProfilePic", codeFellowUser.getProfilePic());
            model.addAttribute("codeFellowUserId", codeFellowUser.getId());

        }

        return "userInformation.html";
    }

    @PutMapping("/users/{id}")  // @DateTimeFormat(pattern = "yyyy-MM-dd")
    public RedirectView editUserInfo(Model m, Principal p, @PathVariable Long id, String username, @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")  LocalDate localDate, RedirectAttributes redir, String firstName, String lastName, String dateOfBirth, String bio, String profilePic)
    {
        if ((p != null) && (p.getName().equals(username)))
        {
            ApplicationUser appUser = appUserJPA.findById(id).orElseThrow();

            appUser.setBio(bio);
            appUser.setFirstName(firstName);
            appUser.setLastName(lastName);
            appUser.setDateOfBirth(dateOfBirth);
            appUser.setUsername(username);

//            appUser.setProfilePic(profilePic);


            // Formatting the LocalDate as a string before setting it
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = localDate.format(formatter);
            appUser.setLocalDate(LocalDate.parse(formattedDate, formatter));

            appUserJPA.save(appUser);
        }
        else
        {
            redir.addFlashAttribute("errorMessage", "Cannot edit another user's Info!");
        }

        return new RedirectView("/users/" + id);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        ResourceNotFoundException(String message) {
            super(message);
        }
    }
}

