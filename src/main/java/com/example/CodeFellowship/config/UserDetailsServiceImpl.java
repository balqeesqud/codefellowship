package com.example.CodeFellowship.config;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.repostories.AppUserJPA;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private AppUserJPA appUserJPA;

    public UserDetailsServiceImpl(AppUserJPA appUserJPA) {
        this.appUserJPA = appUserJPA;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser appUser= appUserJPA.findByUsername(username);

        if(appUser == null){
            System.out.println("User not found "+ username);
            throw new UsernameNotFoundException("user"+ username+ " was not found in the database");
        }
        System.out.println("Found User: "+appUser.getUsername());
        return appUser;
    }


}
