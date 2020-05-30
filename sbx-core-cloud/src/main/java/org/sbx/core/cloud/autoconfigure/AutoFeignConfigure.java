package org.sbx.core.cloud.autoconfigure;

import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sbx.core.cloud.feign.FeignRequestHeaderInterceptor;

/**
 * <p>AutoFeignConfigure class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/25
 */
@Configuration
public class AutoFeignConfigure {


    @Bean
    @ConditionalOnMissingBean
    public RequestInterceptor requestInterceptor(){
        return new FeignRequestHeaderInterceptor();
    }

}
