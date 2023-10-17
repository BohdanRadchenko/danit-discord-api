package com.danit.discord.repository;

import com.danit.discord.entities.User;
import com.danit.discord.entities.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends AppRepository<UserProfile> {
    List<UserProfile> getUserProfilesByUser(User user);

//    boolean existsByEmail(String email);
//
//    boolean existsByUsername(String username);
//
//    boolean existsByEmailOrUsername(String email, String username);
//
//    Optional<User> findUserByUsername(String username);
//
//    Optional<User> findByEmail(String username);
}
