package com.botdiscord.discord.service;

import com.botdiscord.discord.dto.ConviteRequestDTO;
import com.botdiscord.discord.entity.Convite;
import com.botdiscord.discord.repository.ConviteRepository;
import com.botdiscord.discord.utils.QRCodeUtil;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConviteService {

    private final ConviteRepository conviteRepository;
    private final ModelMapper mapper;

    public void criarConvite(ConviteRequestDTO request) {
        conviteRepository.save(mapper.map(request, Convite.class));
    }

    public List<Convite> listarConvites(){
        return conviteRepository.findAll(Sort.by("id").ascending());
    }

    public void editarConvite(ConviteRequestDTO request) {
        conviteRepository.save(mapper.map(request, Convite.class));
    }

    public void deletarConvite(Long id) {
        conviteRepository.deleteById(id);
    }

    public String gerarQRCode() throws WriterException, IOException {
        return QRCodeUtil.gerarQRCodeBase64();
    }

    public Convite acesso(Long id) {
        Convite convite = conviteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Convite não encontrado"));

        LocalDateTime agora = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();

        if (agora.isBefore(convite.getDataEntrada()) || agora.isAfter(convite.getDataSaida())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Convite fora do período válido");
        }

        return convite;
    }
}
