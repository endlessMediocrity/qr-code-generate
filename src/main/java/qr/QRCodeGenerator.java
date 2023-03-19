package qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    public static void generateQRCode(Object data, int width, int height, String filePath, String imageFormat)
            throws WriterException, IOException {
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        if (imageFormat.equalsIgnoreCase("JPEG")) {
            format = BarcodeFormat.PDF_417;
        }

        if (data instanceof String) {
            data = String.valueOf(data);
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(String.valueOf(data), format, width, height, getHints());

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        File qrCodeFile = new File(filePath);
        ImageIO.write(image, imageFormat, qrCodeFile);
    }

    private static Map<EncodeHintType, Object> getHints() {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        return hints;
    }
}

