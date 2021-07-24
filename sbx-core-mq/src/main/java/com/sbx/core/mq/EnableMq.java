package com.sbx.core.mq;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableMqConsumer
@EnableMqProducer
public @interface EnableMq {
}
