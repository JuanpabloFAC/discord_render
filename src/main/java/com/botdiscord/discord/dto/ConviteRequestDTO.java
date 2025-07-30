package com.botdiscord.discord.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConviteRequestDTO {
    private Long id;
    private String nomeConvidado;
    private String cpfConvidado;
    private String tipo;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
}
