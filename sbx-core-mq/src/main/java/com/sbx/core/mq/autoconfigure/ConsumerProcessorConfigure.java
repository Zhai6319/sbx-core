package com.sbx.core.mq.autoconfigure;

import com.sbx.core.model.exception.MessageException;
import com.sbx.core.mq.EnableMqConsumer;
import com.sbx.core.mq.consumer.ConsumerBean;
import com.sbx.core.mq.consumer.ConsumerRegister;
import com.sbx.core.mq.listener.MqMessageListener;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author zhaijianchao
 */
@Configuration
@ConditionalOnBean(annotation = {EnableMqConsumer.class})
public class ConsumerProcessorConfigure {


    @Resource
    private MqConfigProperties mqConfigProperties;

    @Resource
    private ConsumerBean consumerBean;

    @Resource
    private MqMessageListener mqMessageListener;


    @Bean
    public void consumerProcessor(){
        List<String> topics = mqConfigProperties.getConsumer().getTopics();
        if (CollectionUtils.isEmpty(topics)) {
            throw new MessageException("topic is null");
        }
        topics.forEach(topic->{
            String tags = ConsumerRegister.findTagsStrByTopic(topic);
            if (StringUtils.isBlank(tags)) {
                throw new MessageException("缺少tag参数");
            }
            consumerBean.subscribe(topic,tags,mqMessageListener);
        });
        consumerBean.start();
    }
}
