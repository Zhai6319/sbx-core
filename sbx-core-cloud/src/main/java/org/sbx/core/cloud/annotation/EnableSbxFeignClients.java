package org.sbx.core.cloud.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * 开启十店openfeign客户端注解
 * @author zhaijianchao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableFeignClients(basePackages = "org.sbx")
public @interface EnableSbxFeignClients {
}
