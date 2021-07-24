package com.sbx.core.model.base.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/6
 */
public enum BizTypeEnum {

    CARPOOLING(0,"拼车"),
    WHOLE_CAR(1,"整车"),
    SAME_CITY(2,"同城"),
    WITHDRAWAL(3,"提现"),
    RECHARGE(4,"充值"),
    PENALTY(5,"处罚"),
    COMPENSATE(6,"补偿"),
    REFUND(7,"退款"),
    OTHER(99,"其他"),

    ;


    @Getter
    private final Integer code;

    @Getter
    private final String desc;


    BizTypeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static BizTypeEnum getByCode(Integer code) {
        if (Objects.nonNull(code)) {
            for (BizTypeEnum e : BizTypeEnum.values()) {
                if (Objects.equals(e.getCode(),code)) {
                    return e;
                }
            }
        }
        return null;
    }
}
