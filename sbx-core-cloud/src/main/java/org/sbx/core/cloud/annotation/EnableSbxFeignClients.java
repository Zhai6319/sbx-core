package org.sbx.core.cloud.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableFeignClients(basePackages = "org.sbx")
public @interface EnableSbxFeignClients {
}
