package com.sbx.core.pay.ch;

import com.alibaba.fastjson.JSONObject;
import com.sbx.core.model.base.enums.BankTypeEnum;
import com.sbx.core.model.exception.CustomException;
import com.sbx.core.pay.autoconfigure.ChPayProperties;
import com.sbx.core.pay.ch.model.*;
import com.sbx.core.pay.ch.wrappers.*;
import com.sbx.core.tool.util.Base64Util;
import com.sbx.core.tool.util.DigestUtil;
import com.sbx.core.tool.util.http.OKHttpUtil;
import com.sbx.core.pay.ch.enums.BillSubTypeEnum;
import com.sbx.core.pay.ch.enums.BusinessTypeEnum;
import com.sbx.core.pay.ch.enums.ServiceIdEnum;
import com.sbx.core.pay.ch.enums.TerminalTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.*;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/23
 */
@Slf4j
@Component
public class PaymentService {

    @Resource
    private ChPayProperties chPayProperties;


    /**
     * 聚合支付数据
     * @param multiPay  聚合支付数据
     * @param terminalType  终端类型
     * @param billSubType   01：普通订单；02：前置分账；03：后置分账 默认为01
     * @param bankType  银行类型
     * @return  返回聚合支付请求参数
     */
    public ChMultiPayData multiPayData(MultiPay multiPay, BusinessTypeEnum businessType, TerminalTypeEnum terminalType, BillSubTypeEnum billSubType, BankTypeEnum bankType){
        multiPay.setAppId(chPayProperties.getAppId());
        multiPay.setServiceId(ServiceIdEnum.MULTI_PAY.serviceId);
        multiPay.setToAccountNumber(chPayProperties.getAccountNumber());
        multiPay.setSignType(chPayProperties.getSignType());
        multiPay.setBankCode(bankType.getCode());
        multiPay.setBillSubType(billSubType.getCode());
        multiPay.setTerminal(terminalType.name());
        multiPay.setBusinessType(businessType.getType());
        multiPay.setKind(businessType.getKind());
        Map<String,String> params = MultiPayWrapper.buildParams(multiPay);
        String sign = this.signForMD5(params);
        params.put("tf_sign",sign);
        try {
            log.info("交易请求参数:{}",JSONObject.toJSONString(params));
            String result = OKHttpUtil.doPost(chPayProperties.getUrl(),new HashMap<>(),params);
            System.out.println(result);
            BaseResult baseResult = JSONObject.parseObject(result, BaseResult.class);
            if (Objects.equals(baseResult.getCode(),"GP_00")
                    && Objects.equals(baseResult.getBiz_code(),"GPBIZ_00")) {
                MultiPayResultData data = JSONObject.parseObject(baseResult.getData(),MultiPayResultData.class);
                String urlDecodeHttpData = URLDecoder.decode(data.getHtmldata(),"UTF-8");
                String base64DecodeHtmlData = Base64Util.decode(urlDecodeHttpData);
                ChMultiPayData chMultiPayData = new ChMultiPayData();
                chMultiPayData.setSuccess(true);
                chMultiPayData.setBizCode(baseResult.getBiz_code());
                chMultiPayData.setCode(baseResult.getCode());
                chMultiPayData.setMsg(baseResult.getMsg());
                chMultiPayData.setBizMsg(baseResult.getBiz_msg());
                chMultiPayData.setTfSign(data.getTf_sign());
                chMultiPayData.setAppId(data.getAppid());
                chMultiPayData.setPaySn(data.getBusinessrecordnumber());
                chMultiPayData.setOrderSn(data.getBusinessnumber());
                chMultiPayData.setHtmlData(base64DecodeHtmlData);
                return chMultiPayData;
            }
            log.info(result);
            throw new CustomException("发起聚合支付失败："+ baseResult.getMsg()+" "+ baseResult.getBiz_msg());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 查询支付订单
     * @param queryPayOrder 查询参数
     * @return 返回传化支付订单数据
     */
    public QueryPayOrderResultData queryPayOrder(QueryPayOrder queryPayOrder, TerminalTypeEnum terminalType){
        queryPayOrder.setAppId(chPayProperties.getAppId());
        queryPayOrder.setServiceId(ServiceIdEnum.QUERY_PAY_ORDER.serviceId);
        queryPayOrder.setSignType(chPayProperties.getSignType());
        queryPayOrder.setTerminal(terminalType.name());
        Map<String,String> params = QueryPayOrderWrapper.buildParams(queryPayOrder);
        String sign = this.signForMD5(params);
        params.put("tf_sign",sign);
        try {
            String result = OKHttpUtil.doPost(chPayProperties.getUrl(),new HashMap<>(),params);
            BaseResult baseResult = JSONObject.parseObject(result, BaseResult.class);
            if (Objects.equals(baseResult.getCode(),"GP_00")
                    && Objects.equals(baseResult.getBiz_code(),"GPBIZ_00")) {
                return JSONObject.parseObject(baseResult.getData(),QueryPayOrderResultData.class);
            }
            throw new CustomException("查询支付订单失败："+ baseResult.getMsg()+" "+ baseResult.getBiz_msg());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 退款
     * @param param 退款参数
     * @return  返回退款数据
     */
    public RefundResult refund(RefundParam param){
        param.setAppId(chPayProperties.getAppId());
        param.setServiceId(ServiceIdEnum.REFUND.serviceId);
        param.setSignType(chPayProperties.getSignType());
        Map<String,String> params = RefundWrapper.buildParams(param);
        params.put("tf_sign",this.signForMD5(params));
        try {
            String result = OKHttpUtil.doPost(chPayProperties.getUrl(),new HashMap<>(),params);
            log.info("传化退款发起成功："+result);
            BaseResult baseResult = JSONObject.parseObject(result, BaseResult.class);
            if (Objects.equals(baseResult.getCode(),"GP_00")
                    && Objects.equals(baseResult.getBiz_code(),"GPBIZ_00")) {
                return JSONObject.parseObject(baseResult.getData(),RefundResult.class);
            }
            throw new CustomException("退款失败："+ baseResult.getMsg()+" "+ baseResult.getBiz_msg());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 退款
     * @param param 退款参数
     * @return  返回退款数据
     */
    public ClosePayResult closePayOrder(ClosePayParam param){
        param.setAppId(chPayProperties.getAppId());
        param.setServiceId(ServiceIdEnum.REFUND.serviceId);
        param.setSignType(chPayProperties.getSignType());
        Map<String,String> params = ClosePayWrapper.buildParams(param);
        params.put("tf_sign",this.signForMD5(params));
        try {
            String result = OKHttpUtil.doPost(chPayProperties.getUrl(),new HashMap<>(),params);
            log.info("关闭订单成功："+result);
            BaseResult baseResult = JSONObject.parseObject(result, BaseResult.class);
            if (Objects.equals(baseResult.getCode(),"GP_00")
                    && Objects.equals(baseResult.getBiz_code(),"GPBIZ_00")) {
                return JSONObject.parseObject(baseResult.getData(),ClosePayResult.class);
            }
            throw new CustomException("关闭订单失败："+ baseResult.getMsg()+" "+ baseResult.getBiz_msg());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 商户代付到银行卡
     * @param param 请求参数
     * @return  返回结果
     */
    public PayForCustomerResult payForCustomer(PayForCustomerParam param){
        param.setAppId(chPayProperties.getAppId());
        param.setServiceId(ServiceIdEnum.PAY_FOR_CUSTOMER.serviceId);
        param.setSignType(chPayProperties.getSignType());
        param.setFromAccountNumber(chPayProperties.getAccountNumber());
        Map<String, String> params = PayWrapper.buildParams(param);
        params.put("tf_sign",this.signForMD5(params));
        try {
            String result = OKHttpUtil.doPost(chPayProperties.getUrl(),new HashMap<>(),params);
            log.info("商户代付到银行卡成功："+result);
            BaseResult baseResult = JSONObject.parseObject(result, BaseResult.class);
            if (!Objects.equals(baseResult.getResult(),"success")) {
                throw new CustomException("发起提现失败："+ baseResult.getMsg()+" "+ baseResult.getBiz_msg());
            }
            return JSONObject.parseObject(baseResult.getData(),PayForCustomerResult.class);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }



    /**
     * 传化签名签名
     * @param params    签名数据
     * @return  返回签名
     */
    public String signForMD5(Map<String,String> params){
        Map<String,String> signMap = new HashMap<>(params);
        signMap.put("dog_sk",chPayProperties.getDogSk());

        Map<String, String> map = new TreeMap<>();
        for(String key:signMap.keySet()){
            map.put(key, signMap.get(key));
        }
        map = ((TreeMap<String,String>) map).descendingMap();
        Set<Map.Entry<String, String>> set = map.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> me : set) {
            sb.append(me.getValue());
        }
        String signStr = DigestUtil.md5Hex(sb.toString());
        return signStr.toUpperCase();
    }


}
