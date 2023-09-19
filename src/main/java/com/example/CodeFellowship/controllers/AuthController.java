package com.example.CodeFellowship.controllers;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.repostories.AppUserJPA;
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

@Controller
public class AuthController {

private AppUserJPA appUserJPA;

    public AuthController(AppUserJPA appUserJPA) {
        this.appUserJPA = appUserJPA;
    }

    // General Endpoints
      @GetMapping("/signup")
    public String getSignupPage() {
        return "/signup.html";
    }

    @PostMapping("/signup")
    public String signupUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio, String profilePic, LocalDate localDate) {
        String hashedPassword= BCrypt.hashpw(password,BCrypt.gensalt(12));
        ApplicationUser appUser = new ApplicationUser(username, hashedPassword, firstName, lastName, dateOfBirth, bio, profilePic, localDate);
        appUser.setLocalDate(LocalDate.now());
        appUserJPA.save(appUser);
        return "/login.html";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/login.html";
    }

    @PostMapping("/login")
    public RedirectView loggedInUser(HttpServletRequest request, String username, String password,  String firstName, String lastName, String dateOfBirth, String bio, String profilePic, LocalDate localDate){
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
