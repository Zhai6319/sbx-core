package org.sbx.core.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 忽略父类
 * @author Z.jc
 * @since 2020/03/21
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreParentClass {
}
