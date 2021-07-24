package com.sbx.core.pay.ch.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/26
 */
@Data
public class RefundResult implements Serializable {
    private static final long serialVersionUID = -4548646415209524520L;

    /**
     * 传化支付分配给商户的appid
     */
    private String appid;
    /**
     * 业务订单号
     */
    private String businessnumber;
    /**
     * 支付订单号
     */
    private String businessrecordnumber;

}
