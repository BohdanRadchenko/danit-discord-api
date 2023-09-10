package com.danit.discord.repository;

import com.danit.discord.entities.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository<Entity extends AbstractEntity> extends JpaRepository<Entity, Long> {
}
