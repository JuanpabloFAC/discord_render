package com.botdiscord.discord.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class DocLeitorAI {

    private final OpenAiChatModel openAiChatModel;

    public DocLeitorAI(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @PostMapping(value = "/docleitoria", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handlePdfUpload(@RequestPart("file") MultipartFile file, @RequestParam String input, @RequestParam Long usuario) {

        try {
            String extractedText = extractTextFromPdf(file);

            MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                    .maxMessages(10)
                    .build();

            ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

            ChatClient chatClient = ChatClient.builder(openAiChatModel)
                    .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                    .build();

            String response = chatClient.prompt()
                    .user("pdf:" + extractedText + "\n" +
                            "Texto do usuario: " + input)
                    .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, usuario))
                    .call()
                    .content();

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar o PDF.");
        }

    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            return new PDFTextStripper().getText(document);
        }
    }

}
