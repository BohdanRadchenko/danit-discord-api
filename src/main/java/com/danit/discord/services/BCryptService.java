package com.danit.discord.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BCryptService {
    private final PasswordEncoder passwordEncoder;

    public String encode(String string) {
        return passwordEncoder.encode(string);
    }

    public Boolean match(String str, String hash) {
        return passwordEncoder.matches(str, hash);
    }
}
