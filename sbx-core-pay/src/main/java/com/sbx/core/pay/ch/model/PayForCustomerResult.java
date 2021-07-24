package com.sbx.core.pay.ch.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/4/7
 */
@Data
public class PayForCustomerResult implements Serializable {
    private static final long serialVersionUID = -2660280203908163568L;


    private String businessrecordnumber;

    private String transactiondate;

    private String subject;

    private String fromaccountnumber;

    private String appid;

    private String businessnumber;

    private String transactionamount;

    private String frompartyid;

    private String inputdate;

    private String sign_type;

    private String transactiontype;

    private String status;
}
