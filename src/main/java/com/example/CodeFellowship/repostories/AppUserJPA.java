package com.example.CodeFellowship.repostories;

import com.example.CodeFellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AppUserJPA extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);

}
//    ApplicationUser findByFollowingId(Long userId);
//Set <ApplicationUser> findByFollowingId(Long userId);
//    Set <ApplicationUser> findByFollowerId(Long userId);

//}
