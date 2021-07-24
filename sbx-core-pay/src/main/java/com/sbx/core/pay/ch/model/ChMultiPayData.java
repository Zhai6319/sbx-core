package com.sbx.core.pay.ch.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/24
 */
@Data
public class ChMultiPayData implements Serializable {
    private static final long serialVersionUID = 2610420138666033306L;

    /**
     * 是否支付成功
     */
    private Boolean success;

    /**
     * 信息
     */
    private String msg;

    /**
     * 支付响应码
     */
    private String code;

    /**
     * 业务响应码
     */
    private String bizCode;

    /**
     * 业务信息
     */
    private String bizMsg;

    /**
     * 支付号
     */
    private String paySn;

    /**
     * 签名
     */
    private String tfSign;

    /**
     * appid
     */
    private String appId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 支付数据
     */
    private String htmlData;
}
