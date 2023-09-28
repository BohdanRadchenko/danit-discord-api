package com.danit.discord.services;

import com.danit.discord.entities.Chat;
import com.danit.discord.entities.Server;
import com.danit.discord.entities.TextChannel;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.repository.TextChannelRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TextChannelService {
    private final TextChannelRepository textChannelRepository;
    @Lazy
    private final ChatService chatService;

    public TextChannel save(TextChannel channel) {
        return textChannelRepository.save(channel);
    }

    public TextChannel create(Server server) {
        Chat chat = chatService.create();
        return save(TextChannel.create(server, chat));
    }

    public List<TextChannel> get() {
        return textChannelRepository.findAll();
    }

    public TextChannel getByLink(String link) throws NotFoundException {
        Optional<TextChannel> opt = textChannelRepository.getTextChannelByLink(link);
        if (opt.isEmpty()) {
            throw new NotFoundException(String.format("Text channel by link: %s not found!", link));
        }
        return opt.get();
    }
}
