package com.botdiscord.discord.controller;

import com.botdiscord.discord.dto.ConviteRequestDTO;
import com.botdiscord.discord.entity.Convite;
import com.botdiscord.discord.service.ConviteService;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/convite/v1")
@RequiredArgsConstructor
public class ConviteController {

    private final ConviteService conviteService;

    @PostMapping("/criar")
    public ResponseEntity<String> criarConvite(@RequestBody ConviteRequestDTO request) {
        conviteService.criarConvite(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Convite Criado com Sucesso!");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Convite>> listarConvites() {
        List<Convite> conviteList = conviteService.listarConvites();
        return ResponseEntity.status(HttpStatus.OK).body(conviteList);
    }

    @PutMapping("/editar")
    public ResponseEntity<String> editarConvite(@RequestBody ConviteRequestDTO request) {
        conviteService.editarConvite(request);
        return ResponseEntity.status(HttpStatus.OK).body("Convite Atualizado com Sucesso!");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarConvite(@PathVariable Long id) {
        conviteService.deletarConvite(id);
        return ResponseEntity.status(HttpStatus.OK).body("Convite Deletado com Sucesso!");
    }

    @GetMapping("/qrcode")
    public ResponseEntity<String> gerarQRCode() {
        try {
            String qrCodeBase64 = conviteService.gerarQRCode();
            return ResponseEntity.ok(qrCodeBase64);
        } catch (WriterException | IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao gerar QR Code.");
        }
    }

    @GetMapping("/acesso/{id}")
    public ResponseEntity<Convite> acesso(@PathVariable Long id) {
        Convite convite = conviteService.acesso(id);
        return ResponseEntity.ok(convite);
    }

}
