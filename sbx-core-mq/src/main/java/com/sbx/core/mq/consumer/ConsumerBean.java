package com.sbx.core.mq.consumer;

import com.sbx.core.model.exception.MessageException;
import com.sbx.core.mq.api.Consumer;
import com.sbx.core.mq.autoconfigure.MqConfigProperties;
import com.sbx.core.mq.listener.MqMessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@EnableConfigurationProperties(MqConfigProperties.class)
public class ConsumerBean implements Consumer {

    private final ConcurrentHashMap<String, MessageListener> subscribeTable = new ConcurrentHashMap<>();

    private final MqConfigProperties properties;


    @Resource
    private MqMessageListener mqMessageListener;

    private final DefaultMQPushConsumer defaultMQPushConsumer;

    protected final AtomicBoolean started = new AtomicBoolean(false);

    @Autowired
    public ConsumerBean(MqConfigProperties properties){
        this.properties = properties;
        defaultMQPushConsumer = new DefaultMQPushConsumer(properties.getGroupName());
        defaultMQPushConsumer.setNamesrvAddr(this.properties.getNameServerAddr());
        defaultMQPushConsumer.setConsumeThreadMin(this.properties.getConsumer().getThreadMin());
        defaultMQPushConsumer.setConsumeThreadMax(this.properties.getConsumer().getThreadMax());
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(this.properties.getConsumer().getMessageBatchMaxSize());
    }

    @Override
    public boolean isStarted() {
        return this.started.get();
    }

    @Override
    public boolean isClosed() {
        return !this.isStarted();
    }


    @Override
    public void start() {
        try {
            if (this.started.compareAndSet(false, true)) {
                this.defaultMQPushConsumer.setMessageListener(mqMessageListener);
                this.defaultMQPushConsumer.start();
            }
        } catch (Exception var2) {
            throw new MessageException(var2.getMessage());
        }
    }

    @Override
    @PreDestroy
    public void shutdown() {
        if (this.started.compareAndSet(true, false)) {
            this.defaultMQPushConsumer.shutdown();
        }
    }

    @Override
    public void subscribe(String topic, String subExpression, MessageListener listener) {
        if (null == topic) {
            throw new MessageException("订阅消息失败，topic is null");
        } else if (null == listener) {
            throw new MessageException("listener is null");
        } else {
            this.subscribeTable.put(topic, listener);
            try {
                defaultMQPushConsumer.subscribe(topic+"-"+properties.getEnv(), subExpression);
            } catch (MQClientException e) {
                throw new MessageException(e.getErrorMessage());
            }
        }
    }


    @Override
    public void unsubscribe(String topic) {
        defaultMQPushConsumer.unsubscribe(topic);
    }
}
