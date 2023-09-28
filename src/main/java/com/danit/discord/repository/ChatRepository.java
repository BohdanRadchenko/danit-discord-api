package com.danit.discord.repository;

import com.danit.discord.entities.Chat;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends AppRepository<Chat> {
    boolean existsByLink(String link);

    Optional<Chat> getChatByLink(String link);

}
