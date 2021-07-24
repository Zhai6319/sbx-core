package com.sbx.core.sms.properties;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/31
 */
@Data
public class SmsModel implements Serializable {
    private static final long serialVersionUID = -7355489918637910336L;

    /**
     * 模版编码
     */
    private String templateCode;

    /**
     * 模版参数
     */
    private List<String> templateParams;

}
