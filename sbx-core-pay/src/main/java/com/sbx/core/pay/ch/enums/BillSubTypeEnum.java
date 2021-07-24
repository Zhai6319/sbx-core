package com.sbx.core.pay.ch.enums;

import lombok.Getter;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/24
 */
public enum BillSubTypeEnum {

    ORDINARY("01","普通分账"),
    BEFORE("02","前置分账"),
    AFTER("03","后置分账")
    ;
    @Getter
    private final String code;

    @Getter
    private final String name;

    BillSubTypeEnum(String code,String name){
        this.code = code;
        this.name = name;
    }
}
