package com.botdiscord.discord.repository;

import com.botdiscord.discord.entity.Convite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConviteRepository extends JpaRepository<Convite, Long> {
}
