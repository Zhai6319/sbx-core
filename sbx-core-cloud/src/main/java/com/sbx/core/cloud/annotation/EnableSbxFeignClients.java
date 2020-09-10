package com.sbx.core.cloud.annotation;

import com.sbx.core.model.constants.CommonConstant;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;


/**
 * @author zhaijianchao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableFeignClients(basePackages = CommonConstant.PARENT_PACKAGE)
public @interface EnableSbxFeignClients {
}
