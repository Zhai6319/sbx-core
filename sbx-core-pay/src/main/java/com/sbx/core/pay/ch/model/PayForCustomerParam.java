package com.sbx.core.pay.ch.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/4/7
 */
@Data
public class PayForCustomerParam extends BasePayParam{
    private static final long serialVersionUID = 5048464998692372208L;

    /**
     * 业务单号
     */
    private String businessNumber;

    /**
     * 商品名称
     */
    private String subject;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 银行卡号
     */
    private String bankcardNumber;

    /**
     * 银行卡归属人姓名
     */
    private String bankCardName;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行卡类型：个人、企业
     */
    private String bankCardType;

    /**
     * 银行卡借贷类型：储蓄卡、信用卡
     */
    private String bankAccountType;

    /**
     * 支付会员账户
     */
    private String fromAccountNumber;

    /**
     * 回调地址
     */
    private String backUrl;

    /**
     * 支行名称
     */
    private String branchBankName;
}
