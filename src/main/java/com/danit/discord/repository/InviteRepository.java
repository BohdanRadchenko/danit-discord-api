package com.danit.discord.repository;

import com.danit.discord.entities.Chat;
import com.danit.discord.entities.Invite;
import com.danit.discord.entities.User;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends AppRepository<Invite>{
List<Invite> findAllByToId(Long toId);
}
