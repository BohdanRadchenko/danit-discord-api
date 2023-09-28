package com.danit.discord.services;

import com.danit.discord.entities.Chat;
import com.danit.discord.entities.Message;
import com.danit.discord.entities.User;
import com.danit.discord.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public Message create(Chat chat, User user, String message) {
        return save(Message.create(chat, user, message));
    }

}
