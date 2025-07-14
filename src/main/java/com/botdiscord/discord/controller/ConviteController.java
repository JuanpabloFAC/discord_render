package com.botdiscord.discord.controller;

import com.botdiscord.discord.dto.ConviteRequestDTO;
import com.botdiscord.discord.entity.Convite;
import com.botdiscord.discord.service.ConviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/convite")
@RequiredArgsConstructor
public class ConviteController {

    private final ConviteService conviteService;

    @PostMapping("/v1/criar")
    public ResponseEntity<String> criarConvite(@RequestBody ConviteRequestDTO request) {
        conviteService.criarConvite(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Convite Criado com Sucesso!");
    }

    @GetMapping("/v1/listar")
    public ResponseEntity<List<Convite>> listarConvites() {
        List<Convite> conviteList = conviteService.listarConvites();
        return ResponseEntity.status(HttpStatus.OK).body(conviteList);
    }

}
