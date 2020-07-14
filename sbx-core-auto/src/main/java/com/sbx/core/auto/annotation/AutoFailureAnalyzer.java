package com.sbx.core.auto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * FailureAnalyzer 处理
 *
 * @author Z.jc
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface AutoFailureAnalyzer {
}
