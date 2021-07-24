package com.sbx.core.tool.util.qr;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/27
 */
public class BarCodeUtil {

    /**
     * 生成条形码输出流
     * @param out
     */
    public static void createBarCode(CodeParam codeParams, OutputStream out){
        try {
            Map<EncodeHintType, Object> hints = getDecodeHintType(codeParams);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeParams.getText(), BarcodeFormat.CODE_128, codeParams.getWidth(), codeParams.getHeight(),hints);
            MatrixToImageWriter.writeToStream(bitMatrix, codeParams.getQrcodeFormat(), out);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 生成条形码图片
     */
    public static void createBarCodeImg(CodeParam codeParams, String barPath){
        try {

            Map<EncodeHintType, Object> hints = getDecodeHintType(codeParams);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeParams.getText(), BarcodeFormat.CODE_128, codeParams.getWidth(), codeParams.getHeight(), hints);
            File file = new File(barPath);
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedImage image = new BufferedImage( codeParams.getWidth(), codeParams.getHeight(), BufferedImage.TYPE_INT_RGB);
            ImageIO.write(image, codeParams.getQrcodeFormat(), file);
            MatrixToImageWriter.writeToFile(bitMatrix, codeParams.getQrcodeFormat(), file);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 针对条形码进行解析
     *
     * @param imgPath
     * @return
     */
    public static String decodeBar(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);
            System.out.println(result.getText());
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    public static Map<EncodeHintType, Object> getDecodeHintType(CodeParam codeParams)
    {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, codeParams.getLevel());
        hints.put(EncodeHintType.MARGIN, codeParams.getMargin());
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        return hints;
    }
}
