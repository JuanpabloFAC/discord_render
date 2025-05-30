package com.botdiscord.discord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Imovel {

    private String endereco;
    private Double areaTotal;
    private Integer numeroDeQuartos;
    private Integer numeroDeBanheiros;
    private Boolean possuiVaranda;
    private Boolean possuiSuite;
    private Boolean possuiGaragem;
    private Integer vagasDeGaragem;
    private String tipoDeConstrucao;
    private Integer andar;
    private Boolean possuiElevador;
    private Double valorVenda;
    private Double valorCondominio;
    private Integer anoDeConstrucao;
    private String descricao;
    private Date ultimaGeracao;

}