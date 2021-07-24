package com.sbx.core.pay.ch.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/25
 */
@Data
public class BasePayParam implements Serializable {
    private static final long serialVersionUID = 585547086745337555L;
    /**
     * 1001000	传化支付分配给商户的appid
     */
    private String appId;
    /**
     * 时间戳格式：yyyyMMddHHmmss
     */
    private String tfTimestamp;
    /**
     * tf56pay.gateway. multiPay	服务名称
     */
    private String serviceId;
    /**
     * 签名
     */
    private String tfSign;
    /**
     * RSA	商户生成签名字符串所使用的签名算法类型
     */
    private String signType;
    /**
     * 终端类型：
     *     PC、Android、IOS、WP、EPOS、POS、ETC、PARK、MJ、SCANNER、CARD、H5
     */
    private String terminal;
    /**
     * 版本号 01
     */
    private String version;

    /**
     * 客户端请求IP
     */
    private String clientIp;

}
