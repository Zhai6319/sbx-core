package com.sbx.core.mq;

import com.sbx.core.mq.consumer.ConsumerBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ConsumerBean.class)
public @interface EnableMqConsumer {
}
