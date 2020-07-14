package com.sbx.core.oss.autoconfigure;

import com.sbx.core.oss.component.AliOssHelper;
import com.sbx.core.oss.component.properties.SbxOssProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/5/4
 */
@Configuration
@EnableConfigurationProperties(SbxOssProperties.class)
public class AutoOssConfigure {

    @Bean
    public AliOssHelper aliOssHelper(){
        return new AliOssHelper();
    }

}
