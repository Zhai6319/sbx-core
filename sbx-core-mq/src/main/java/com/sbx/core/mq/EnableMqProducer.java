package com.sbx.core.mq;

import com.sbx.core.mq.producer.ProducerBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ProducerBean.class)
public @interface EnableMqProducer {
}
