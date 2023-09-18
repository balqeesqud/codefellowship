package com.example.CodeFellowship.repostories;

import com.example.CodeFellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserJPA extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}
