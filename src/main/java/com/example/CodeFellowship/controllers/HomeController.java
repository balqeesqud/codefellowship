package com.example.CodeFellowship.controllers;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.models.Post;
import com.example.CodeFellowship.repostories.AppUserJPA;
import com.example.CodeFellowship.repostories.PostJPA;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    private AppUserJPA appUserJPA;
    private PostJPA postJPA;

    public HomeController(AppUserJPA appUserJPA) {
        this.appUserJPA = appUserJPA;
    }

    // General Endpoints
    @GetMapping("/home")
    public String getHomePage() {
        return "index.html";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "about.html";
    }

    @GetMapping("/securedAbout")
    public String getAboutSecuredPage() {
        return "aboutAfterLogging.html";
    }

    // Secured Home Endpoint
    @GetMapping("/securedHome")
    public String getHomePageAfterLogging() {
        return "indexAfterLogging.html";
    }

//    @GetMapping("/myposts")
//    public String fellowPostsPage(Model model, HttpServletRequest request){
//        HttpSession session= request.getSession();
//        String username= session.getAttribute("username").toString();
//        model.addAttribute("username", username);
//        ApplicationUser appUserInDb= appUserJPA.findByUsername(username);
//        List<Post> userPostsInDB = appUserInDb.getPosts();
//        model.addAttribute("userPosts", userPostsInDB);
////        model.addAttribute("createdAt",(LocalDate.now()));
//        List<Post> userPosts = postJPA.findAll();
//        model.addAttribute("userPosts", userPosts);
//        return "createposts.html";
//
//    }

    @GetMapping("/myposts")
    public String fellowPostsPage(Model model, Principal p) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser appUserInDB = appUserJPA.findByUsername(username);

            if (appUserInDB != null) {
                List<Post> userPostsInDB = appUserInDB.getPosts();
                model.addAttribute("username", username);
                model.addAttribute("userPosts", userPostsInDB);
                model.addAttribute("createdAt", LocalDate.now());
            }
        }
        return "createposts.html";

    }

//    @GetMapping("/editInfo")
//    public String editInfo(Model model, Principal p){
//        if (p != null) {
//            String username = p.getName();
//            ApplicationUser appUser = appUserJPA.findByUsername(username);
//
//            model.addAttribute("createdDate", appUser.getLocalDate());
//            model.addAttribute("username", username);
//            model.addAttribute("firstName", appUser.getFirstName());
//            model.addAttribute("lastName", appUser.getLastName());
//            model.addAttribute("dateOfBirth", appUser.getDateOfBirth());
//            model.addAttribute("bio", appUser.getBio());
//            model.addAttribute("codeFellowUserId", appUser.getId());
//        }
//            return "editInfo.html";
//    }
}








