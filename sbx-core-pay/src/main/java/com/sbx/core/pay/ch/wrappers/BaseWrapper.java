package com.sbx.core.pay.ch.wrappers;

import com.sbx.core.model.validator.Validator;
import com.sbx.core.pay.ch.enums.TerminalTypeEnum;
import com.sbx.core.pay.ch.model.BasePayParam;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/26
 */
public class BaseWrapper {

    public static <T extends BasePayParam> Map<String,String> buildParams(T param){
        Validator.getInstance()
                .notBlank(param.getAppId(),"appId")
                .notBlank(param.getServiceId(),"serviceId")
                .notBlank(param.getSignType(),"signType")
                .notBlank(param.getTfTimestamp(),"tfTimestamp");
        Map<String,String> params = new HashMap<>();
        params.put("appid",param.getAppId());
        params.put("service_id",param.getServiceId());
        params.put("sign_type",param.getSignType());
        params.put("terminal",StringUtils.isBlank(param.getTerminal()) ? TerminalTypeEnum.PC.name() : param.getTerminal());
        params.put("tf_timestamp",param.getTfTimestamp());
        if (StringUtils.isNotBlank(param.getVersion())) {
            params.put("version",param.getVersion());
        }
        return params;
    }
}
