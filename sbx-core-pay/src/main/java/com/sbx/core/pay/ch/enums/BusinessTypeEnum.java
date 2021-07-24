package com.sbx.core.pay.ch.enums;

import lombok.Getter;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/24
 */
public enum BusinessTypeEnum {
    BUSINESS_SERVICES("商家消费","商业服务消费");

    @Getter
    private final String type;

    @Getter
    private final String kind;

    BusinessTypeEnum(String type,String kind) {
        this.type = type;
        this.kind = kind;
    }
}
