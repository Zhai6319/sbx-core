package com.sbx.core.cloud.autoconfigure;


import com.sbx.core.cloud.feign.FeignRequestHeaderInterceptor;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
