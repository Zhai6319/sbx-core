package org.sbx.core.sms.helper;

import com.alibaba.alicloud.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang3.StringUtils;
import org.sbx.core.model.exception.BusinessException;
import org.sbx.core.model.exception.SecurityException;
import org.sbx.core.redis.cache.SbxRedisCache;
import org.sbx.core.sms.properties.SmsProperties;
import org.sbx.core.tool.util.TxtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/11
 */
public class SmsHelper {

    private static final String SMS_VERIFY_CODE = "sbx:sms:code:mobile:";


    @Autowired
    private ISmsService smsService;
    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private SbxRedisCache sbxRedisCache;

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
        request.setTemplateCode(smsProperties.getTemplateCode().get("verify-code"));
        // Required:The param of sms template.For exmaple, if the template is "Hello,your verification code is ${code}". The param should be like following value
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            SendSmsResponse sendSmsResponse =  smsService.sendSmsRequest(request);
            sbxRedisCache.setEx(SMS_VERIFY_CODE+phone,code,5*60L);
            return sendSmsResponse;
        }
        catch (ClientException e) {
            throw new BusinessException(e.getMessage());
        }
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
