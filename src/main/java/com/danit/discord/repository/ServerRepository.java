package com.danit.discord.repository;

import com.danit.discord.entities.Server;
import com.danit.discord.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerRepository extends AppRepository<Server> {

}
