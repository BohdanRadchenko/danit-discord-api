package com.danit.discord.services;

import com.danit.discord.dto.text.TextChannelResponse;
import com.danit.discord.entities.Chat;
import com.danit.discord.entities.Server;
import com.danit.discord.entities.TextChannel;
import com.danit.discord.entities.User;
import com.danit.discord.exceptions.ForbiddenException;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.repository.TextChannelRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TextChannelService {
    private final TextChannelRepository textChannelRepository;
    @Lazy
    private final ChatService chatService;

    public TextChannel save(TextChannel channel) {
        return textChannelRepository.save(channel);
    }

    public TextChannel create(Server server, User user) {
        Chat chat = chatService.create();
        return save(TextChannel.create(server, chat, user));
    }

    public List<TextChannelResponse> get() {
        return textChannelRepository
                .findAll()
                .stream()
                .map(TextChannelResponse::of)
                .collect(Collectors.toList());
    }

    public TextChannelResponse getByLink(String link, String userEmail) throws NotFoundException, ForbiddenException {
        Optional<TextChannel> opt = textChannelRepository.getTextChannelByLink(link);
        if (opt.isEmpty()) {
            throw new NotFoundException(String.format("Text channel by link: %s not found!", link));
        }
        TextChannel channel = opt.get();
        Optional<User> user = channel
                .getUsers()
                .stream()
                .filter(u -> u.getEmail().equals(userEmail))
                .findAny();
        if (user.isEmpty()) {
            throw new ForbiddenException("FORBIDDEN");
        }
        return TextChannelResponse.of(channel);
    }
}