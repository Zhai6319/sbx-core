package com.sbx.core.pay.ch.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/26
 */
@Data
public class RefundParam extends BasePayParam{

    private static final long serialVersionUID = -3277939581913717712L;
    /**
     * 传化支付主动通知商户服务器交易结果,回调地址
     */
    private String backUrl;
    /**
     * 原业务订单号
     */
    private String businessNumber;
    /**
     * 退款业务订单号
     */
    private String refundBusinessNumber;
    /**
     * 客户端请求IP
     */
    private String clientIp;
    /**
     * 退款金额，部分退款必传
     */
    private BigDecimal refundAmount;


}
