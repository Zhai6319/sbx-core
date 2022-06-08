package com.sbx.core.pay.ch.wrappers;

import com.sbx.core.model.exception.CustomException;
import com.sbx.core.model.validator.Validator;
import com.sbx.core.model.base.enums.BankTypeEnum;
import com.sbx.core.pay.ch.model.MultiPay;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/24
 */
public class MultiPayWrapper {

    public static Map<String,Object> buildParams(MultiPay multiPay){
        Validator.getInstance()
                .notBlank(multiPay.getSubject(),"subject")
                .notBlank(multiPay.getBankCode(),"bankCode")
                .notBlank(multiPay.getBusinessType(),"businessType")
                .notBlank(multiPay.getKind(),"kind")
                .notBlank(multiPay.getBusinessNumber(),"businessNumber")
                .notNull(multiPay.getTransactionAmount(),"transactionAmount");

        Map<String,Object> params = BaseWrapper.buildParams(multiPay);
        params.put("backurl",multiPay.getBackUrl());
        params.put("fronturl",multiPay.getFrontUrl());
        params.put("businesstype",multiPay.getBusinessType());
        params.put("kind",multiPay.getKind());
        params.put("clientip",multiPay.getClientIp());
        params.put("merchtonline",StringUtils.isBlank(multiPay.getMerchtOnline()) ? "0" : multiPay.getMerchtOnline());
        params.put("businessnumber",multiPay.getBusinessNumber());
        params.put("transactionamount",multiPay.getTransactionAmount().toString());
        params.put("toaccountnumber",multiPay.getToAccountNumber());
        params.put("bankcode",multiPay.getBankCode());
        params.put("subject",multiPay.getSubject());
        BankTypeEnum bankType = BankTypeEnum.getByCode(multiPay.getBankCode());
        if (Objects.isNull(bankType)) {
            throw new CustomException("bankCode错误");
        }
        switch (bankType) {
            case WXXCX:{
                if (StringUtils.isBlank(multiPay.getWxAppId()) || StringUtils.isBlank(multiPay.getWxOpenId())) {
                    throw new CustomException("缺少微信支付必要参数");
                }
                params.put("wxappid",multiPay.getWxAppId());
                params.put("wxopenid",multiPay.getWxOpenId());
                break;
            }
        }
        if (StringUtils.isNotBlank(multiPay.getGoodsDetail())) {
            params.put("goodsdetail",multiPay.getGoodsDetail());
        }


        if (StringUtils.isNotBlank(multiPay.getDescription())) {
            params.put("description",multiPay.getDescription());
        }
        if (StringUtils.isNotBlank(multiPay.getAggregationCode())) {
            params.put("aggregationcode",multiPay.getAggregationCode());
        }
        if (StringUtils.isNotBlank(multiPay.getShopName())) {
            params.put("shopname",multiPay.getShopName());
        }
        if (StringUtils.isNotBlank(multiPay.getBillSubType())) {
            params.put("billsubtype",multiPay.getBillSubType());
        }
        if (StringUtils.isNotBlank(multiPay.getShareInfo())) {
            params.put("shareinfo",multiPay.getShareInfo());
        }
        if (StringUtils.isNotBlank(multiPay.getMerchantUserId())) {
            params.put("merchantuserid",multiPay.getMerchantUserId());
        }
        if (StringUtils.isNotBlank(multiPay.getMerchantEmail())) {
            params.put("merchantemail",multiPay.getMerchantEmail());
        }
        if (StringUtils.isNotBlank(multiPay.getMerchtDeviceName())) {
            params.put("merchtdevicename",multiPay.getMerchtDeviceName());
        }
        if (StringUtils.isNotBlank(multiPay.getMerchtDeviceValue())) {
            params.put("merchtdevicevalue",multiPay.getMerchtDeviceValue());
        }
        if (StringUtils.isNotBlank(multiPay.getDeliveryAddrFull())) {
            params.put("deliveryaddrfull",multiPay.getDeliveryAddrFull());
        }
        if (StringUtils.isNotBlank(multiPay.getDeliveryAddrPro())) {
            params.put("deliveryaddrpro",multiPay.getDeliveryAddrPro());
        }
        if (StringUtils.isNotBlank(multiPay.getDeliveryAddrCity())) {
            params.put("deliveryaddrcity",multiPay.getDeliveryAddrCity());
        }
        if (StringUtils.isNotBlank(multiPay.getDeliveryAddrDis())) {
            params.put("deliveryaddrdis",multiPay.getDeliveryAddrDis());
        }
        if (StringUtils.isNotBlank(multiPay.getDeliveryAddrStr())) {
            params.put("deliveryaddrstr",multiPay.getDeliveryAddrStr());
        }
        if (StringUtils.isNotBlank(multiPay.getDeliveryName())) {
            params.put("deliveryname",multiPay.getDeliveryName());
        }
        if (StringUtils.isNotBlank(multiPay.getDeliveryPhone())) {
            params.put("deliveryphone",multiPay.getDeliveryPhone());
        }
        return params;
    }
}
