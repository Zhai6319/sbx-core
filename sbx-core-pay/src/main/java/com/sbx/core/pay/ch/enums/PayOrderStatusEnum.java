package com.sbx.core.pay.ch.enums;

import com.sbx.core.tool.util.StringUtil;

import java.util.Objects;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/25
 */
public enum PayOrderStatusEnum {
    APPLIED("已申请"),
    SUCCESS("成功"),
    FAIL("失败"),
    REFUND("已退款"),
    CLOSED("关闭")
    ;

    public String status;

    PayOrderStatusEnum(String status) {
        this.status = status;
    }

    public static PayOrderStatusEnum getByCode(String status) {
        if (StringUtil.isNotBlank(status)) {
            for (PayOrderStatusEnum item : PayOrderStatusEnum.values()) {
                if (Objects.equals(item.status,status)) {
                    return item;
                }
            }
        }
        return null;
    }


}
