package com.sbx.core.pay.ch.wrappers;

import com.sbx.core.model.validator.Validator;
import com.sbx.core.pay.ch.model.PayForCustomerParam;
import com.sbx.core.tool.util.StringUtil;

import java.util.Map;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/4/7
 */
public class PayWrapper extends BaseWrapper{


    public static Map<String,Object> buildParams(PayForCustomerParam param){
        Validator.getInstance()
                .notBlank(param.getTfTimestamp(),"tfTimestamp")
                .notBlank(param.getBusinessNumber(),"businessNumber");
        Map<String,Object> params = BaseWrapper.buildParams(param);
        params.put("businessnumber",param.getBusinessNumber());
        params.put("clientip",param.getClientIp());
        params.put("subject",param.getSubject());
        params.put("transactionamount",param.getTransactionAmount().toString());
        params.put("bankcardnumber",param.getBankcardNumber());
        params.put("bankcardname",param.getBankCardName());
        params.put("bankname",param.getBankName());
        params.put("bankcardtype",param.getBankCardType());
        params.put("bankaccounttype",param.getBankAccountType());
        params.put("fromaccountnumber",param.getFromAccountNumber());
        if (StringUtil.isNotBlank(param.getBackUrl())) {
            params.put("backurl",param.getBackUrl());
        }
        if (StringUtil.isNotBlank(param.getBranchBankName())) {
            params.put("branchbankname",param.getBranchBankName());
        }
        return params;
    }
}
