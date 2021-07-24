package com.sbx.core.pay.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/23
 */
@Data
@ConfigurationProperties(prefix = "ch")
public class ChPayProperties {

    /**
     * 传化appid
     */
    private String appId;
    /**
     * 传化支付地址
     */
    private String url;

    /**
     * 账户号
     */
    private String accountNumber;

    /**
     * 签名类型 MD5
     */
    private String signType;

    /**
     * 密钥
     */
    private String dogSk;


}
