package com.danit.discord.services;

import com.danit.discord.dto.auth.RegisterRequest;
import com.danit.discord.dto.profile.UserProfileRequest;
import com.danit.discord.dto.user.UserResponse;
import com.danit.discord.entities.User;
import com.danit.discord.entities.UserProfile;
import com.danit.discord.exceptions.AlreadyExistException;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Lazy
    private final UserProfileService userProfileService;
    private final UserRepository repository;

    private boolean isExistByUsername(String username) {
        return repository.existsByUsername(username);
    }

    private boolean isExistByEmail(String email) {
        return repository.existsByEmail(email);
    }

    private void alreadyExistByEmail(String email) {
        if (isExistByEmail(email)) {
            throw new AlreadyExistException(
                    String.format("User with email %s already exist!", email)
            );
        }
    }

    private void alreadyExistByUsername(String username) {
        if (isExistByUsername(username)) {
            throw new AlreadyExistException(
                    String.format("User with username %s already exist!", username)
            );
        }
    }

    public List<UserResponse> getAllUsers() {
        return repository
                .findAll()
                .stream()
                .map(UserResponse::of)
                .collect(Collectors.toList());
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User create(RegisterRequest registerRequest) {
        alreadyExistByEmail(registerRequest.getEmail());
        alreadyExistByUsername(registerRequest.getUsername());
        User newUser = User
                .builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .passwordHash(registerRequest.getPassword())
                .build();
        User user = save(newUser);
        UserProfileRequest profileRequest = UserProfileRequest
                .builder()
                .name(registerRequest.getName())
                .build();
        UserProfile profile = userProfileService.create(user, profileRequest);
        user.getProfiles().add(profile);
        return user;
    }

    public User getByEmail(String email) throws NotFoundException {
        Optional<User> userOptional = repository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new NotFoundException(
                    String.format("User with email %s not found!", email)
            );
        }
        return userOptional.get();
    }

    public User getById(Long id) throws NotFoundException {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException(
                    String.format("User with id %s not found!", id)
            );
        }
        return userOptional.get();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, NotFoundException {
        User user = getByEmail(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
