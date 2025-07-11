package com.botdiscord.discord.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permitir todas as origens (ou apenas de origem específica, caso necessário)
        registry.addMapping("/api/**")
                .allowedOrigins("*")  // Permitindo todas as origens
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Métodos permitidos
                .allowedHeaders("*")  // Permitindo todos os cabeçalhos
                .allowCredentials(true); // Permitir credenciais (se necessário)
    }
}
