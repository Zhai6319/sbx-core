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
public class BaseResult implements Serializable {
    private static final long serialVersionUID = -7077462941972942102L;

    private String code;

    private String biz_code;

    private String msg;

    private String biz_msg;

    private String data;

    private String result;

    private String count;

}
