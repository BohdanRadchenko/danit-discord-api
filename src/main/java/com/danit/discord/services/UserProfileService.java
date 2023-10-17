package com.danit.discord.services;

import com.danit.discord.dto.profile.ProfileResponse;
import com.danit.discord.dto.profile.UserProfileRequest;
import com.danit.discord.entities.User;
import com.danit.discord.entities.UserProfile;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.repository.UserProfileRepository;
import com.danit.discord.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repository;
    @Lazy
    private final UserRepository userRepository;
    @Lazy
    private final FileUpload fileUpload;

    private User getUser(Principal principal) throws NotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(principal.getName());
        if (userOptional.isEmpty()) {
            throw new NotFoundException(
                    String.format("User with email %s not found!", principal.getName())
            );
        }
        return userOptional.get();
    }

    public List<UserProfile> findAll(Principal principal) {
        User user = getUser(principal);
        List<UserProfile> profiles = user.getProfiles();
        if (profiles.isEmpty()) {
            throw new NotFoundException("User profile not found!");
        }
        return profiles;
    }

    public ProfileResponse getAll(Principal principal) {
        return ProfileResponse.of(findAll(principal));
    }

    private UserProfile save(UserProfile profile) {
        return repository.save(profile);
    }

    public UserProfile create(User user, UserProfileRequest request) {
        UserProfile profile = UserProfile.create(user, request);
        return save(profile);
    }

    private UserProfile updateUserProfile(UserProfile profile, UserProfileRequest form) throws IOException {
        if (form.getName().isPresent()) {
            profile.setBanner(form.getName().get());
        }
        if (form.getPronouns().isPresent()) {
            profile.setBanner(form.getPronouns().get());
        }
        if (form.getDescription().isPresent()) {
            profile.setBanner(form.getDescription().get());
        }
        if (form.getBanner().isPresent()) {
            profile.setBanner(form.getBanner().get());
        }
        if (form.getAvatar().isPresent()) {
            String[] split = profile.getAvatar().split("\"/\"");
            String avatarId = profile.getUser().getId().toString();

            if (!split[split.length - 1].equals(avatarId)) {
                System.out.println("update avatar");
                String url = fileUpload.uploadAvatarImage(form.getAvatar().get(), avatarId);
                profile.setAvatar(url);
            }
        }
        return profile;
    }

    @SneakyThrows
    public ProfileResponse updateProfile(UserProfileRequest form, Principal principal, String serverId) throws NotFoundException {
        List<UserProfile> profiles = findAll(principal);

        final String INITIAL_SEVER_BADGE = "INITIAL";
        Map<String, UserProfile> profilesMap = new HashMap<>();
        for (UserProfile profile : profiles) {
            String server = profile.getServer() == null ? INITIAL_SEVER_BADGE : profile.getServer().toString();
            profilesMap.put(server, profile);
        }

        String currentServerId = serverId == null ? INITIAL_SEVER_BADGE : serverId;
        UserProfile profile = profilesMap.get(currentServerId) == null
                ? profilesMap.get(INITIAL_SEVER_BADGE)
                : profilesMap.get(currentServerId);

        if (profile == null) {
            throw new NotFoundException(String.format("Profile for '%s' not found!", principal.getName()));
        }

        if (serverId != null) {
            profile.setServer(Long.parseLong(serverId));
        }

        save(profile);
        return ProfileResponse.of(getUser(principal).getProfiles());
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
