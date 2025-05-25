package com.botdiscord.discord.service;

import com.botdiscord.discord.dto.AlunoRequestDTO;
import com.botdiscord.discord.dto.AlunoResponseDTO;
import com.botdiscord.discord.entity.Aluno;
import com.botdiscord.discord.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoResponseDTO create(AlunoRequestDTO dto) {
        Aluno aluno = Aluno.builder()
                .nome(dto.nome())
                .idade(dto.idade())
                .build();
        aluno = repository.save(aluno);
        return new AlunoResponseDTO(aluno.getId(), aluno.getNome(), aluno.getIdade());
    }

    public List<AlunoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(a -> new AlunoResponseDTO(a.getId(), a.getNome(), a.getIdade()))
                .toList();
    }

    public AlunoResponseDTO getById(Long id) {
        Aluno a = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        return new AlunoResponseDTO(a.getId(), a.getNome(), a.getIdade());
    }

    public AlunoResponseDTO update(Long id, AlunoRequestDTO dto) {
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        aluno.setNome(dto.nome());
        aluno.setIdade(dto.idade());
        aluno = repository.save(aluno);

        return new AlunoResponseDTO(aluno.getId(), aluno.getNome(), aluno.getIdade());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
