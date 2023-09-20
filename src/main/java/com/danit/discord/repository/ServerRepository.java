package com.danit.discord.repository;

import com.danit.discord.entities.Server;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository extends AppRepository<Server> {

    public List<Server> findAllByOwnerEmail(String email);
}
