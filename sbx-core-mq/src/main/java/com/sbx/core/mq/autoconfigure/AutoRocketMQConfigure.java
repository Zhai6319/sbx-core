package com.sbx.core.mq.autoconfigure;

import com.sbx.core.launch.factory.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;



/**
 * @author zhaijianchao
 */
@Configuration
@EnableConfigurationProperties(MqConfigProperties.class)
@PropertySource(value = "classpath:sbx-mq.yaml",factory = YamlPropertySourceFactory.class)
public class AutoRocketMQConfigure {



}
