package com.sbx.core.redis.annotation;

import com.sbx.core.redis.lock.impl.RedisNestedLocker;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        RedisNestedLocker.class
})
public @interface EnableRedis {
}
