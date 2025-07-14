package com.botdiscord.discord.service;

import com.botdiscord.discord.dto.ConviteRequestDTO;
import com.botdiscord.discord.entity.Convite;
import com.botdiscord.discord.repository.ConviteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConviteService {

    private final ConviteRepository conviteRepository;
    private final ModelMapper mapper;

    public void criarConvite(ConviteRequestDTO request) {
        conviteRepository.save(mapper.map(request, Convite.class));
    }

    public List<Convite> listarConvites(){
        return conviteRepository.findAll();
    }
}
