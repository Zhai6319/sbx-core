package com.sbx.core.sms.autoconfigure;

import com.sbx.core.sms.helper.SmsHelper;
import com.sbx.core.sms.properties.SmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/11
 */
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class AutoSmsConfigure {

    /**
     * 短信助手
     * @return
     */
    @Bean
    public SmsHelper smsHelper(){
        return new SmsHelper();
    }

}
