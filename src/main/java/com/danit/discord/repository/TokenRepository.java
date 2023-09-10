package com.danit.discord.repository;

import com.danit.discord.entities.Token;
import com.danit.discord.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends AppRepository<Token> {
}
