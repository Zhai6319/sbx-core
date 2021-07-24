package com.sbx.core.pay.ch.enums;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/25
 */
public enum ServiceIdEnum {

    MULTI_PAY("tf56pay.gateway.multiPay","聚合支付"),
    QUERY_PAY_ORDER("tf56pay.gateway.orderQuery","查询支付订单"),
    REFUND("tf56pay.gateway.orderRefund","传化退款"),
    PAY_FOR_CUSTOMER("tf56enterprise.enterprise.payForCustomer","商户代付到银行卡"),
    BAR_CODE_PAY("tf56pay.gateway.barCodePay","条码支付"),
    ;
    public final String serviceId;

    public final String desc;

    ServiceIdEnum(String serviceId,String desc){
        this.serviceId = serviceId;
        this.desc = desc;
    }
}
