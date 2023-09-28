package com.danit.discord.repository;

import com.danit.discord.entities.TextChannel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TextChannelRepository extends AppRepository<TextChannel> {
    Optional<TextChannel> getTextChannelByLink(String link);

    boolean existsByLink(String link);
}
