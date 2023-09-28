package com.danit.discord.repository;

import com.danit.discord.entities.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends AppRepository<Message> {
}
