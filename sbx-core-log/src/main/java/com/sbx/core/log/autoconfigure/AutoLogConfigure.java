package com.sbx.core.log.autoconfigure;

import com.sbx.core.launch.factory.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>AutoLogCofigure class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/4
 */
@Configuration
@PropertySource(value = "classpath:sbx-log.yaml",factory = YamlPropertySourceFactory.class)
public class AutoLogConfigure {


}
