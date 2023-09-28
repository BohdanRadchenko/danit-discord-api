package com.danit.discord.services;

import com.danit.discord.dto.channel.ChannelInviteRequest;
import com.danit.discord.dto.text.TextChannelResponse;
import com.danit.discord.entities.Chat;
import com.danit.discord.entities.Server;
import com.danit.discord.entities.TextChannel;
import com.danit.discord.entities.User;
import com.danit.discord.exceptions.AlreadyExistException;
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
    @Lazy
    private final UserService userService;

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

    public TextChannel getByLink(String link, String userEmail) throws NotFoundException, ForbiddenException {
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
        return channel;
    }

    public TextChannelResponse getByLinkResponse(String link, String userEmail) throws NotFoundException, ForbiddenException {
        return TextChannelResponse.of(getByLink(link, userEmail));
    }

    public TextChannel invite(String link, String userEmail, ChannelInviteRequest request) throws NotFoundException, ForbiddenException, AlreadyExistException {
        TextChannel channel = getByLink(link, userEmail);
        User user = userService.getById(request.getId());
        Optional<User> existUser = channel.getUsers().stream().filter(u -> u.getId().equals(request.getId())).findAny();
        if (existUser.isPresent()) {
            throw new AlreadyExistException(String.format("User with username: '%s' already exist in '%s' channel!", user.getUserName(), channel.getTitle()));
        }
        channel.addUser(user);
        return save(channel);
    }
}