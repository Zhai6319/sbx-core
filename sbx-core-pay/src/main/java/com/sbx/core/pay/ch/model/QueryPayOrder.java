package com.sbx.core.pay.ch.model;

import lombok.Data;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/25
 */
@Data
public class QueryPayOrder extends BasePayParam {
    private static final long serialVersionUID = 4195145324602944584L;

    /**
     * 业务订单号，需保证在商户端不重复
     */
    private String businessNumber;


}
