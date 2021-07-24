package com.sbx.core.pay.ch.model;

import lombok.Data;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/5
 */
@Data
public class ClosePayParam extends BasePayParam{
    private static final long serialVersionUID = 8321055438749931511L;

    /**
     * 业务订单号，需保证在商户端不重复，需保证在商户端不重复
     */
    private String businessNumber;

    /**
     * 备注
     */
    private String description;

}
