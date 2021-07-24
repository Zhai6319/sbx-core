package com.sbx.core.pay.ch.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/25
 */
@Data
public class QueryPayOrderResultData implements Serializable {
    private static final long serialVersionUID = 337285724503653000L;

    /**
     * 传化支付分配给商户的appid
     */
    private String appid;
    /**
     * 终端类型：
     *     PC、Android、IOS、WP、EPOS、POS、ETC、PARK、MJ、SCANNER、CARD、H5
     */
    private String terminal;
    /**
     * 业务订单号，需保证在商户端不重复
     */
    private String businessnumber;

    /**
     * 支付订单号
     */
    private String businessrecordnumber;
    /**
     * 电子产品(笔记本)	商品名称
     */
    private String subject;
    /**
     * 	描述说明
     */
    private String description;
    /**
     * 成功	交易状态：成功/关闭/已退款/失败
     */
    private String status;
    /**
     * 订单金额
     */
    private BigDecimal billamount;
    /**
     * 交易金额：单位：元，保留两位小数
     */
    private BigDecimal transactionamount;
    /**
     * 消费	交易类型
     */
    private String transactiontype;
    /**
     * 交易最新时间，格式为yyyy-MM-dd HH:mm:ss
     */
    private String transactiondate;
    /**
     * 交易创建时间，格式为yyyy-MM-dd HH:mm:ss
     */
    private String inputdate;
    /**
     * 退款金额
     */
    private BigDecimal refundamount;
    /**
     * 付款方会员id
     */
    private String frompartyid;
    /**
     * 原订单号
     */
    private String originalrecordnumber;
    /**
     * 付款方会员账号
     */
    private String fromaccountnumber;
    /**
     * 收款方会员id
     */
    private String topartyid;
    /**
     * 收款方会员账号
     */
    private String toaccountnumber;
    private String businesstype;
    /**
     * 消费场景
     */
    private String kind;
    /**
     * 结果说明
     */
    private String remark;
}
