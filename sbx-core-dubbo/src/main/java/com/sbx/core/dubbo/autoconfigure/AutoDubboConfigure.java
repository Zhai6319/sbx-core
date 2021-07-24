package com.sbx.core.dubbo.autoconfigure;

import com.sbx.core.launch.factory.YamlPropertySourceFactory;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/8/17
 */
@EnableDubbo
@Configuration
@PropertySource(value = "classpath:sbx-dubbo.yaml",factory = YamlPropertySourceFactory.class)
public class AutoDubboConfigure {




}
