package com.botdiscord.discord.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String msg = event.getMessage().getContentRaw().toLowerCase();
        String user = event.getAuthor().getName();

        switch (msg) {
            case "!ping" -> event.getChannel().sendMessage("🏓 Pong!").queue();
            case "!hello" -> event.getChannel().sendMessage("Olá, " + user + "! 👋").queue();
            case "!help" -> event.getChannel().sendMessage("""
                    📜 Comandos disponíveis:
                    • !ping → Responde com Pong!
                    • !hello → Te cumprimenta.
                    • !sobre → Informações do bot.
                    • !server → Nome e membros do servidor.
                    """).queue();
            case "!sobre" -> event.getChannel().sendMessage("🤖 Feito com Java + Spring Boot + JDA").queue();
            case "!server" -> {
                String serverName = event.getGuild().getName();
                int members = event.getGuild().getMemberCount();
                event.getChannel().sendMessage("🌐 Servidor: **" + serverName + "** com " + members + " membros.").queue();
            }
        }
    }

}
