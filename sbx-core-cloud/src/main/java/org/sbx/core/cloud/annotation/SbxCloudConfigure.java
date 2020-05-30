package org.sbx.core.cloud.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.lang.annotation.*;

/**
 * 启动spring cloud 应用程序
 * @author zhaijianchao
 * @since 2020/03/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableSbxFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public @interface SbxCloudConfigure {
}
