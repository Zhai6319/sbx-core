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
public class MultiPayResultData implements Serializable {
    private static final long serialVersionUID = 4900152830560262611L;

    private String businessrecordnumber;

    private String tf_sign;

    private String appid;

    private String businessnumber;

    private String htmldata;


}
