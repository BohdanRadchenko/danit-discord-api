package com.danit.discord.services;

import com.danit.discord.dto.auth.RegisterRequest;
import com.danit.discord.entities.User;
import com.danit.discord.exceptions.AlreadyExistException;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
                .passwordHash(registerRequest.getPassword())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
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
