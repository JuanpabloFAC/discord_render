package com.botdiscord.discord.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "convites")
public class Convite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_convidado", nullable = false)
    private String nomeConvidado;

    @Column(name = "cpf_convidado", nullable = false, unique = true)
    private String cpfConvidado;

    @Column(nullable = true)
    private String qrcode;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;
}