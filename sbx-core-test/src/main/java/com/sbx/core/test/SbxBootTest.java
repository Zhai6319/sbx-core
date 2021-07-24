package com.sbx.core.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/1/21
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest
public @interface SbxBootTest {

    @AliasFor("appName")
    String value() default "sbx-test";

    @AliasFor("value")
    String appName() default "sbx-test";

    String profile() default "dev";

    boolean enableLoader() default false;
}
