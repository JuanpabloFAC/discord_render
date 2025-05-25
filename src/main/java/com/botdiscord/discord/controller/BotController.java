package com.botdiscord.discord.controller;

import com.botdiscord.discord.service.DiscordBotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    private final DiscordBotService discordBotService;

    public BotController(DiscordBotService discordBotService) {
        this.discordBotService = discordBotService;
    }

    @GetMapping("/start")
    public String startBot() {
        return discordBotService.startBot();
    }

}
