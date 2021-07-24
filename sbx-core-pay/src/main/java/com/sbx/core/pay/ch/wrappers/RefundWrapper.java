package com.sbx.core.pay.ch.wrappers;


import com.sbx.core.model.validator.Validator;
import com.sbx.core.pay.ch.model.RefundParam;

import java.util.Map;
import java.util.Objects;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/25
 */
public class RefundWrapper {

    public static Map<String,String> buildParams(RefundParam param){
        Validator.getInstance()
                .notBlank(param.getTfTimestamp(),"tfTimestamp")
                .notBlank(param.getRefundBusinessNumber(),"refundBusinessNumber")
                .notBlank(param.getBackUrl(),"backUrl")
                .notBlank(param.getClientIp(),"clientIp")
                .notBlank(param.getBusinessNumber(),"businessNumber");
        Map<String,String> params = BaseWrapper.buildParams(param);
        if (Objects.nonNull(param.getRefundAmount())) {
            params.put("refundamount",param.getRefundAmount().toString());
        }
        params.put("backurl",param.getBackUrl());
        params.put("businessnumber",param.getBusinessNumber());
        params.put("refundbusinessnumber",param.getRefundBusinessNumber());
        params.put("clientip",param.getClientIp());
        return params;
    }
}
