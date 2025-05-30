package com.botdiscord.discord.controller;

import com.botdiscord.discord.dto.Imovel;
import com.botdiscord.discord.service.JasperReportService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class PdfGeneratorAI {

    private final JasperReportService jasperReportService;
    private final OpenAiChatModel openAiChatModel;

    public PdfGeneratorAI(JasperReportService jasperReportService, OpenAiChatModel openAiChatModel) {
        this.jasperReportService = jasperReportService;
        this.openAiChatModel = openAiChatModel;
    }


    @PostMapping("/pdfgeneratorai")
    public ResponseEntity<String> gerarPdfJasper(@RequestBody Imovel imovel) throws IOException {

        String insightIa = "Me dê um insight inteligente do meu imóvel, irei te passar as características dele abaixo, na resposta não coloque o texto em negrito: ";
        String caracteristicasImovel = imovel.toString();

        Prompt prompt = new Prompt(insightIa + caracteristicasImovel);
        ChatResponse chatResponse = openAiChatModel.call(prompt);
        String output = chatResponse.getResult().getOutput().getText();
        imovel.setDescricao(output);
        return ResponseEntity.ok(jasperReportService.gerarPdf(imovel));
    }

}
