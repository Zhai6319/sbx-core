package com.sbx.core.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/11
 */
@Data
@ConfigurationProperties(prefix = "sbx.sms")
public class SmsProperties {

    private String signName;

    private Map<String,SmsModel> key = new HashMap<>();

}
