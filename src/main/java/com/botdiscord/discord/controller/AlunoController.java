package com.botdiscord.discord.controller;

import com.botdiscord.discord.dto.AlunoRequestDTO;
import com.botdiscord.discord.dto.AlunoResponseDTO;
import com.botdiscord.discord.service.AlunoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService service;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<AlunoResponseDTO> create(@RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> getAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> update(@PathVariable Long id, @RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
