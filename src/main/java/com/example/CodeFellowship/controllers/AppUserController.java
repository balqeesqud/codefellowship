package com.example.CodeFellowship.controllers;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.repostories.AppUserJPA;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Controller
public class AppUserController {

private AppUserJPA appUserJPA;

    public AppUserController(AppUserJPA appUserJPA) {
        this.appUserJPA = appUserJPA;
    }

    // General Endpoints
    @GetMapping("/home")
    public String getHomePage(){
        return "index.html";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about.html";
    }
//    @GetMapping("/Securedabout")
//    public String getAboutSecuredPage(){
//        return "about.html";
//    }

    // Secured Home Endpoint
    @GetMapping("/securedHome")
    public String getHomePageAfterLogging(){
        return "indexAfterLogging.html";
    }




    @GetMapping("/userInfo")
    public String getUserInfo(Model model, HttpServletRequest request) {
        // the next line we use it because we have used spring security dependency, without it we will get null
        final String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        HttpSession httpSession = request.getSession();


        ApplicationUser userInDB = appUserJPA.findByUsername(currentUser);
        model.addAttribute("username", userInDB);

        return "userInformation.html";
    }



  // Auth Endpoints

    @GetMapping("/signup")
    public String getSignupPage() {
        return "/signup.html";
    }

    @PostMapping("/signup")
    public String signupUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) {
        String hashedPassword= BCrypt.hashpw(password,BCrypt.gensalt(12));
        ApplicationUser appUser = new ApplicationUser(username, hashedPassword, firstName, lastName, dateOfBirth, bio);
        appUserJPA.save(appUser);
        return "/login.html";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/login.html";
    }

    @PostMapping("/login")
    public RedirectView loggedInUser(HttpServletRequest request, String username, String password,  String firstName, String lastName, String dateOfBirth, String bio){
        ApplicationUser userInDB = appUserJPA.findByUsername(username);

        if((userInDB == null)
                || !(BCrypt.checkpw(password, userInDB.getPassword())))
        {
            return new RedirectView("/login");
        }
        HttpSession httpSession= request.getSession();
        httpSession.setAttribute("username", username);
        return new RedirectView("/securedHome");
    }


    @PostMapping("/logout")
    public RedirectView logoutUser(HttpServletRequest request){

        HttpSession session= request.getSession();
        session.invalidate();

        return new RedirectView("/login");
    }
    @GetMapping("/logout")
    public String getLogoutPage(){
        return "/login.html";
    }
}
