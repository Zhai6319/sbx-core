package com.sbx.core.pay.ch.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/23
 */
@Data
public class MultiPay extends BasePayParam {
    private static final long serialVersionUID = -2643932616366595571L;

    /**
     * http://www.test.com/notify	传化支付主动通知商户服务器交易结果,回调地址
     */
    private String backUrl;
    /**
     * http://www.test.com/result.html	页面跳转同步通知页面路径
     */
    private String frontUrl;
    /**
     * 电子产品(笔记本)	商品名称
     */
    private String subject;
    /**
     * 业务类型，请参考第4章节
     */
    private String businessType;
    /**
     * 消费场景，请参考第4章节
     */
    private String kind;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 业务订单号，需保证在商户端不重复，需保证在商户端不重复
     */
    private String businessNumber;
    /**
     * 交易金额：单位：元，保留两位小数
     */
    private BigDecimal transactionAmount;
    /**
     *
     */
    private String toAccountNumber;
    /**
     * 银行编号，请参考银行码表
     */
    private String bankCode;
    /**
     * 多级代理商编号
     */
    private String aggregationCode;
    /**
     * 客户端请求IP
     */
    private String clientIp;
    /**
     * 分店名称
     */
    private String shopName;
    /**
     * 微信、支付宝选传，json列表格式，
     * 具体字段如下，
     * 样例：[{goods_id:商品编码,goods_name:商品名称,quantity:商品数量,price:商品单价}]
     * 商品详情
     */
    private String goodsDetail;
    /**
     * 01：普通订单；02：前置分账；03：后置分账 默认为01
     */
    private String billSubType;

    /**
     * 分账信息，格式：分账账号，分账金额
     * 当billsubtype=02时该字段必填，分账总金额等于交易金额
     */
    private String shareInfo;


    private String wxAppId;

    private String wxOpenId;

    /**
     * 在商户平台中用户id
     */
    private String merchantUserId;
    /**
     * 在商户平台中用户邮箱地址
     */
    private String merchantEmail;
    /**
     * 支付设备类型：
     * PC端传MAC
     * 手机端传IMEI
     * IOS设备传UDID
     */
    private String merchtDeviceName;
    /**
     * 支付设备类型对应的值
     */
    private String merchtDeviceValue;
    /**
     * 0-线上交易，1-线下交易（实体零售，扫码支付等）
     */
    private String merchtOnline;
    /**
     * 收货地址全名
     */
    private String deliveryAddrFull;
    /**
     * 收货人地址省
     */
    private String deliveryAddrPro;
    /**
     * 收货地址市级
     */
    private String deliveryAddrCity;
    /**
     * 收货地址县/区
     */
    private String deliveryAddrDis;
    /**
     * 收货地址街道
     */
    private String deliveryAddrStr;
    /**
     * 收货人姓名
     */
    private String deliveryName;
    /**
     * 收货人联系手机
     */
    private String deliveryPhone;



}
