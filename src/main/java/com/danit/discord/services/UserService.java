package com.danit.discord.services;

import com.danit.discord.dto.auth.RegisterRequest;
import com.danit.discord.dto.channel.ChannelInviteRequest;
import com.danit.discord.dto.user.UserInviteRequest;
import com.danit.discord.dto.user.UserResponse;
import com.danit.discord.entities.TextChannel;
import com.danit.discord.entities.User;
import com.danit.discord.exceptions.AlreadyExistException;
import com.danit.discord.exceptions.ForbiddenException;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private boolean isExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean isExistByEmail(String email) {
        return userRepository.existsByEmail(email);
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
        return userRepository
                .findAll()
                .stream()
                .map(UserResponse::of)
                .collect(Collectors.toList());
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(RegisterRequest registerRequest) {
        alreadyExistByEmail(registerRequest.getEmail());
        alreadyExistByUsername(registerRequest.getUsername());
        User user = User
                .builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .name(registerRequest.getName())
                .passwordHash(registerRequest.getPassword())
                .build();
        return save(user);
    }

    public User getByEmail(String email) throws NotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new NotFoundException(
                    String.format("User with email %s not found!", email)
            );
        }
        return userOptional.get();
    }

    public User getById(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
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

    public void addFriend(User u1, User u2) {
            u1.getFriends().add(u2);
            u2.getFriends().add(u1);
    }

    public void removeFriend(User u1, User u2 ) {
            u1.getFriends().remove(u2);
            u2.getFriends().remove(u1);
    }

    public List<User> getAllFriends(User user){
        return  user.getFriends();
    }
    public void add(String userEmail, UserInviteRequest request) throws NotFoundException, ForbiddenException, AlreadyExistException {
        User userFrom = getByEmail(userEmail);
        User userTo = getById(request.getId());
        Optional<User> existUser = userFrom.getFriends().stream().filter(u -> u.getId().equals(request.getId())).findAny();
        if (existUser.isPresent()) {
            throw new AlreadyExistException(String.format("User with username: '%s' already exist added to your friend", userTo.getUserName()));
        } else {
            addFriend(userFrom, userTo);
            save(userFrom);
            save(userTo);
        }
    }
    public void remove(String userEmail, UserInviteRequest request) throws NotFoundException, ForbiddenException, AlreadyExistException {
        User userFrom = getByEmail(userEmail);
        User userTo = getById(request.getId());
       // Optional<User> existUser = userFrom.getFriends().stream().filter(u -> u.getId().equals(request.getId())).findAny();
        /*if (existUser.isPresent()) {
            throw new AlreadyExistException(String.format("User with username: '%s' already exist added to your friend", userTo.getUserName()));
        } else {*/
            removeFriend(userFrom, userTo);
            save(userFrom);
            save(userTo);
        //}
    }
}