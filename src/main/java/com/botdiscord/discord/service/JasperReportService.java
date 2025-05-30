package com.botdiscord.discord.service;

import com.botdiscord.discord.dto.Imovel;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.IOUtils;
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
            JasperReport jasperReport = JasperCompileManager.compileReport(caminho);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
            return encodeFileToBase64(byteArrayOutputStream.toByteArray());
        } catch(JRException e) {
            throw new RuntimeException(e);
        }

    }

    private byte[] loadimagem(String image) throws IOException{

        String imagem = ResourceUtils.getFile(IMAGEM).getAbsolutePath();
        File file = new File(imagem);
        try(InputStream inputStream = new FileInputStream(file)){
            return IOUtils.toByteArray(inputStream);
        }
    }

    private String getAbsolutePath() throws FileNotFoundException {
        return ResourceUtils.getFile(RELATORIO + ARQUIVOJRXML).getAbsolutePath();
    }

    private String encodeFileToBase64(byte[] fileData) {
        return Base64.getEncoder().encodeToString(fileData);
    }

}
