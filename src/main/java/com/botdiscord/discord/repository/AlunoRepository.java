package com.botdiscord.discord.repository;

import com.botdiscord.discord.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
