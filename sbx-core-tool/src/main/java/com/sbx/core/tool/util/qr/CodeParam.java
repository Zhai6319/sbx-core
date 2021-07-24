package com.sbx.core.tool.util.qr;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/27
 */
@Data
public class CodeParam implements Serializable {

    private String text;
    private String qrcodeFormat = "png";
    private String qrCodeUrl;
    private String filePath;
    private String fileName;
    private String logoPath;
    private Integer width = 300;
    private Integer height = 300;
    private Integer onColor = 0xFF000000;
    private Integer offColor = 0xFFFFFFFF;
    private Integer margin = 1;
    //二维码容错率 L = ~7% /M = ~15% /Q = ~25% /H = ~30% 容错率越高,二维码的有效像素点就越多
    private ErrorCorrectionLevel level = ErrorCorrectionLevel.L;
}
