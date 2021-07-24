package com.sbx.core.sms.helper;

import com.alibaba.cloud.spring.boot.sms.ISmsService;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.sbx.core.model.exception.CustomException;
import com.sbx.core.model.exception.SecurityException;
import com.sbx.core.sms.properties.SmsModel;
import com.sbx.core.tool.util.CollectionUtil;
import com.sbx.core.sms.properties.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.sbx.core.redis.cache.SbxRedisCache;
import com.sbx.core.tool.util.TxtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Map;
import java.util.Objects;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/11
 */
@Slf4j
@RefreshScope
public class SmsHelper {

    private static final String SMS_VERIFY_CODE = "sbx:sms:code:mobile:";


    @Autowired
    private ISmsService smsService;
    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private SbxRedisCache sbxRedisCache;

    @Value("${sbx.sms.enable:true}")
    private Boolean enableSms;

    /**
     * 发生短信验证码
     * @param phone 手机号
     * @param code 验证码
     * @return
     */
    public SendSmsResponse sendCode(String phone,String code){

        SendSmsRequest request = new SendSmsRequest();
        // Required:the mobile number
        request.setPhoneNumbers(phone);
        // Required:SMS-SignName-could be found in sms console
        request.setSignName(smsProperties.getSignName());
        // Required:Template-could be found in sms console
        SmsModel smsModel = smsProperties.getKey().get("verify-code");
        request.setTemplateCode(smsModel.getTemplateCode());
        // Required:The param of sms template.For exmaple, if the template is "Hello,your verification code is ${code}". The param should be like following value
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            SendSmsResponse sendSmsResponse =  smsService.sendSmsRequest(request);
            sbxRedisCache.setEx(SMS_VERIFY_CODE+phone,code,5*60L);
            return sendSmsResponse;
        }
        catch (ClientException e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 发生短信
     * @param phone 手机号
     * @param param 请求参数 不通模版请求参数不通
     * @return
     */
    public SendSmsResponse sendSms(String phone, Map<String,String> param,String templateKey){
        if (!enableSms) {
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        // Required:the mobile number
        request.setPhoneNumbers(phone);
        // Required:SMS-SignName-could be found in sms console
        request.setSignName(smsProperties.getSignName());
        log.info("短信签名："+smsProperties.getSignName());
        // Required:Template-could be found in sms console
        SmsModel smsModel = smsProperties.getKey().get(templateKey);
        if (CollectionUtil.isNotEmpty(smsModel.getTemplateParams())) {
            for (String templateParam : smsModel.getTemplateParams()) {
                String checkParam = param.get(templateParam);
                if (Objects.isNull(checkParam)) {
                    throw new CustomException("参数 " + templateParam + " 不可为空");
                }
            }
        }
        request.setTemplateCode(smsModel.getTemplateCode());
        // Required:The param of sms template.For exmaple, if the template is "Hello,your verification code is ${code}". The param should be like following value
        request.setTemplateParam(JSONObject.toJSONString(param));
        try {
            SendSmsResponse response = smsService.sendSmsRequest(request);
            log.info("发送短信结果：{}",JSONObject.toJSONString(response));
            return response;
        } catch (ClientException e) {
            log.error("发送短信失败",e);
        }
        return null;
    }

    /**
     * 根据手机号和短信验证码判断是否正确
     * @param phone
     * @param code
     * @return
     */
    public boolean verifySmsCode(String phone,String code){
        if (!TxtUtil.isMobile(phone)) {
            throw new SecurityException("手机号格式错误");
        }
        String verifyCode = sbxRedisCache.get(SMS_VERIFY_CODE+phone);
        if (StringUtils.isBlank(verifyCode)) {
            throw new SecurityException("未获取验证码");
        }
        if (Objects.equals(verifyCode,code)) {
            sbxRedisCache.del(SMS_VERIFY_CODE+phone);
            return true;
        }
        if (StringUtils.isBlank(verifyCode)) {
            throw new SecurityException("未获取验证码");
        }
        return false;
    }

}
