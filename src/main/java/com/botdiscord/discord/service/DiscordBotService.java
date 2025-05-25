package com.botdiscord.discord.service;

import com.botdiscord.discord.listener.MessageListener;
import jakarta.annotation.PreDestroy;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.dv8tion.jda.api.utils.cache.CacheFlag;


@Service
public class DiscordBotService {

    @Value("${discord.token}")
    private String token;

    private JDA jda;

    public String startBot() {
        if (jda != null && jda.getStatus().isInit()) {
            return "Bot já está em execução.";
        }

        try {
            jda = jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .setActivity(Activity.playing("via REST!"))
                    .addEventListeners(new MessageListener())
                    .build();
            return "Bot iniciado!";
        } catch (InvalidTokenException e) {
            return "Token inválido!";
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    @PreDestroy
    public void shutdown() {
        if (jda != null) jda.shutdown();
    }
}