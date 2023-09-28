package com.danit.discord.services;

import com.danit.discord.dto.message.MessageResponse;
import com.danit.discord.dto.room.RoomMessage;
import com.danit.discord.entities.Chat;
import com.danit.discord.entities.User;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageService messageService;
    @Lazy
    private final UserService userService;

    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat create() {
        return save(Chat.create());
    }

    public void existByLink(String link) {
        boolean isExist = chatRepository.existsByLink(link);
        if (!isExist) {
            throw new NotFoundException(String.format("Room %s not found", link));
        }
    }

    public Chat getByLink(String link) throws NotFoundException {
        Optional<Chat> opt = chatRepository.getChatByLink(link);
        if (opt.isEmpty()) {
            throw new NotFoundException(String.format("Chat by link %s not found!", link));
        }
        return opt.get();
    }

    public MessageResponse createMessage(String link, RoomMessage rm) throws NotFoundException {
        Chat chat = getByLink(link);
        User user = userService.getById(rm.getFrom());
        return MessageResponse.of(messageService.create(chat, user, rm.getMessage()));
    }
}
