package com.botdiscord.discord.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ChatAIController {

    private final OpenAiChatModel openAiChatModel;

    public ChatAIController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("/openai")
    public ResponseEntity<String> getResponseAi(@RequestParam String input, @RequestParam Long usuario) {

        MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();

        ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

        ChatClient chatClient = ChatClient.builder(openAiChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        String response = chatClient.prompt()
                .user(input)
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, usuario))
                .call()
                .content();

        return ResponseEntity.ok(response);
    }

}
