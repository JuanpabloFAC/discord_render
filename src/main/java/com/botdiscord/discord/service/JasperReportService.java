package com.botdiscord.discord.service;

import com.botdiscord.discord.dto.Imovel;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperReportService {

    public static final String RELATORIO = "classpath:jasper/relatorio/";
    public static final String IMAGEM = "classpath:jasper/img/logopmt.jpg";
    public static final String ARQUIVOJRXML = "relatorio.jrxml";

    public String gerarPdf(Imovel imovel) throws IOException {

        byte[] imagem = this.loadimagem(IMAGEM);

        Map<String, Object> params = new HashMap<>();
        params.put("ultimaGeracao", imovel.getUltimaGeracao());
        params.put("endereco", imovel.getEndereco());
        params.put("areaTotal", imovel.getAreaTotal());
        params.put("numeroDeQuartos", imovel.getNumeroDeQuartos());
        params.put("numeroDeBanheiros", imovel.getNumeroDeBanheiros());
        params.put("valorVenda", imovel.getValorVenda());
        params.put("valorCondominio", imovel.getValorCondominio());
        params.put("anoDeConstrucao", imovel.getAnoDeConstrucao());
        params.put("vagasDeGaragem", imovel.getVagasDeGaragem());
        params.put("descricao", imovel.getDescricao());
        params.put("imageJasper", imagem);

        String caminho = getAbsolutePath();

        try {
            JasperReport jasperReport = getJasperReport();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
            return encodeFileToBase64(byteArrayOutputStream.toByteArray());
        } catch(JRException e) {
            throw new RuntimeException(e);
        }

    }

    private byte[] loadimagem(String imagePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(imagePath.replace("classpath:", ""));
        try (InputStream inputStream = resource.getInputStream()) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    private JasperReport getJasperReport() throws JRException, IOException {
        String pathSemClasspath = RELATORIO.replace("classpath:", "") + ARQUIVOJRXML;
        ClassPathResource resource = new ClassPathResource(pathSemClasspath);
        try (InputStream inputStream = resource.getInputStream()) {
            return JasperCompileManager.compileReport(inputStream);
        }
    }

    private String getAbsolutePath() throws FileNotFoundException {
        return ResourceUtils.getFile(RELATORIO + ARQUIVOJRXML).getAbsolutePath();
    }

    private String encodeFileToBase64(byte[] fileData) {
        return Base64.getEncoder().encodeToString(fileData);
    }

}
