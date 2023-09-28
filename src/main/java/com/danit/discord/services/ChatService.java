package com.danit.discord.services;

import com.danit.discord.entities.Chat;
import com.danit.discord.repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat create() {
        return save(Chat.create());
    }
}
