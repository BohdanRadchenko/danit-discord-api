package com.danit.discord.services;

import com.danit.discord.dto.server.ServerRequest;
import com.danit.discord.dto.server.ServerResponse;
import com.danit.discord.entities.Server;
import com.danit.discord.entities.User;
import com.danit.discord.repository.ServerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServerService {
    private final ServerRepository serverRepository;
    @Lazy
    private final UserService userService;

    //TODO: remove next code after implementation invite
//    private List<User> serverUsers;

    public Server save(Server server) {
        return serverRepository.save(server);
    }

    public ServerResponse create(ServerRequest serverRequest, Principal principal) {
        User user = userService.getByEmail(principal.getName());
        Server server = Server.of(serverRequest, user);
        return ServerResponse.of(save(server));
    }

    public List<ServerResponse> getByUserEmail(String email) {
        List<Server> servers = serverRepository.findAllByOwnerEmail(email);
        return servers
                .stream()
                .map(ServerResponse::of)
                .collect(Collectors.toList());
    }

    //TODO: remove next code after implementation invite
//    public void serverInvite(User user, boolean answer) {
//        if (answer) serverUsers.add(userRepository.getReferenceById(user.getId()));
//    }
}
