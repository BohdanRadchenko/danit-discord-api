package com.danit.discord.services;

import com.danit.discord.dto.profile.UserProfileRequest;
import com.danit.discord.dto.profile.UserProfileResponse;
import com.danit.discord.entities.User;
import com.danit.discord.entities.UserProfile;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.repository.UserProfileRepository;
import com.danit.discord.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repository;
    @Lazy
    private final UserRepository userRepository;

    private User getUser(Principal principal) {
        Optional<User> userOptional = userRepository.findByEmail(principal.getName());
        if (userOptional.isEmpty()) {
            throw new NotFoundException(
                    String.format("User with email %s not found!", principal.getName())
            );
        }
        return userOptional.get();
    }

    public UserProfileResponse get(Principal principal) {
        User user = getUser(principal);
        List<UserProfile> profiles = user.getProfiles();
        if (profiles.isEmpty()) {
            throw new NotFoundException("User profile not found!");
        }
        return UserProfileResponse.of(profiles);
    }

    public UserProfile create(User user, UserProfileRequest request) {
        UserProfile profile = UserProfile.create(user, request);
        return repository.save(profile);
    }

//    private boolean isExistByUsername(String username) {
//        return userRepository.existsByUsername(username);
//    }
//
//    private boolean isExistByEmail(String email) {
//        return userRepository.existsByEmail(email);
//    }
//
//    private void alreadyExistByEmail(String email) {
//        if (isExistByEmail(email)) {
//            throw new AlreadyExistException(
//                    String.format("User with email %s already exist!", email)
//            );
//        }
//    }
//
//    private void alreadyExistByUsername(String username) {
//        if (isExistByUsername(username)) {
//            throw new AlreadyExistException(
//                    String.format("User with username %s already exist!", username)
//            );
//        }
//    }
//
//    public List<UserResponse> getAllUsers() {
//        return userRepository
//                .findAll()
//                .stream()
//                .map(UserResponse::of)
//                .collect(Collectors.toList());
//    }
//
//    public User save(User user) {
//        return userRepository.save(user);
//    }
//
//    public User create(RegisterRequest registerRequest) {
//        alreadyExistByEmail(registerRequest.getEmail());
//        alreadyExistByUsername(registerRequest.getUsername());
//        User user = User
//                .builder()
//                .email(registerRequest.getEmail())
//                .username(registerRequest.getUsername())
//                .name(registerRequest.getName())
//                .passwordHash(registerRequest.getPassword())
//                .build();
//        return save(user);
//    }
//
//    public User getByEmail(String email) throws NotFoundException {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isEmpty()) {
//            throw new NotFoundException(
//                    String.format("User with email %s not found!", email)
//            );
//        }
//        return userOptional.get();
//    }
//
//    public User getById(Long id) throws NotFoundException {
//        Optional<User> userOptional = userRepository.findById(id);
//        if (userOptional.isEmpty()) {
//            throw new NotFoundException(
//                    String.format("User with id %s not found!", id)
//            );
//        }
//        return userOptional.get();
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, NotFoundException {
//        User user = getByEmail(username);
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .build();
//    }
}
