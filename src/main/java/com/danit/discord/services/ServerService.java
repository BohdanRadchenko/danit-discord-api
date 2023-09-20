package com.danit.discord.services;

import com.danit.discord.dto.server.ServerRequest;
import com.danit.discord.dto.server.ServerResponse;
import com.danit.discord.entities.Server;
import com.danit.discord.entities.User;
import com.danit.discord.repository.ServerRepository;
import com.danit.discord.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServerService {
    private final ServerRepository serverRepository;
    private final UserRepository userRepository;

    private List<User> serverUsers;

    public Server save(Server server) {
        return serverRepository.save(server);
    }

    public ServerResponse create(ServerRequest serverRequest) {
        System.out.println("serverRequest " + serverRequest);
        Server server = Server.of(serverRequest);
        return ServerResponse.of(save(server));
    }

    public void serverInvite(User user, boolean answer) {
        if (answer) serverUsers.add(userRepository.getReferenceById(user.getId()));
    }


    public List<User> findAllUsers() {
        return serverUsers;
    }


}
