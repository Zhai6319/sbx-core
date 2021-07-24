package com.sbx.core.pay.ch.wrappers;


import com.sbx.core.model.validator.Validator;
import com.sbx.core.pay.ch.model.QueryPayOrder;

import java.util.Map;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/25
 */
public class QueryPayOrderWrapper {

    public static Map<String,String> buildParams(QueryPayOrder queryPayOrder){
        Validator.getInstance()
                .notBlank(queryPayOrder.getTfTimestamp(),"tfTimestamp")
                .notBlank(queryPayOrder.getBusinessNumber(),"businessNumber");
        Map<String,String> params = BaseWrapper.buildParams(queryPayOrder);
        params.put("businessnumber",queryPayOrder.getBusinessNumber());
        return params;
    }
}
