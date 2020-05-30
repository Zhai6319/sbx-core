package org.sbx.core.db.autoconfigure;

import org.sbx.core.launch.factory.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>AutoDbConfigure class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/24
 */
@Configuration
@PropertySource(value = "classpath:sbx-db.yaml",factory = YamlPropertySourceFactory.class)
public class AutoDbConfigure {

}
