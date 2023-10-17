package com.danit.discord.repository;

import com.danit.discord.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AppRepository<User> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);

    Optional<User> findUserByUsername(String username);

    Optional<User> findByEmail(String username);
}
