package com.sbx.core.pay.ch.wrappers;

import com.sbx.core.model.validator.Validator;
import com.sbx.core.pay.ch.model.ClosePayParam;

import java.util.Map;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/5
 */
public class ClosePayWrapper {

    public static Map<String,Object> buildParams(ClosePayParam param){
        Validator.getInstance()
                .notBlank(param.getTfTimestamp(),"tfTimestamp")
                .notBlank(param.getBusinessNumber(),"businessNumber");
        Map<String,Object> params = BaseWrapper.buildParams(param);
        params.put("businessnumber",param.getBusinessNumber());
        params.put("clientip",param.getClientIp());
        params.put("description",param.getDescription());
        return params;
    }
}
