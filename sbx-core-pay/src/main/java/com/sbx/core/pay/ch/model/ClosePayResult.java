package com.sbx.core.pay.ch.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/5
 */
@Data
public class ClosePayResult implements Serializable {
    private static final long serialVersionUID = 7009480140230794190L;

    /**
     * 1001000	传化支付分配给商户的appid
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
