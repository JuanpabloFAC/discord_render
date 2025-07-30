package com.botdiscord.discord.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {

    public static String gerarQRCodeBase64()
            throws WriterException, IOException {

        String url = "https://meusite.com/convite/123";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 300, 300, hints);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        byte[] qrImageBytes = outputStream.toByteArray();
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(qrImageBytes);
    }
}
