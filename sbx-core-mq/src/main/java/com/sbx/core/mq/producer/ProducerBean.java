package com.sbx.core.mq.producer;

import com.sbx.core.model.exception.MessageException;
import com.sbx.core.mq.api.Producer;
import com.sbx.core.mq.autoconfigure.MqConfigProperties;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@EnableConfigurationProperties(MqConfigProperties.class)
public class ProducerBean implements Producer {

    private final MqConfigProperties properties;

    private final DefaultMQProducer defaultMQProducer;

    protected final AtomicBoolean started = new AtomicBoolean(false);

    public ProducerBean(MqConfigProperties properties) {
        this.properties = properties;
        this.defaultMQProducer = new DefaultMQProducer(properties.getGroupName());
        defaultMQProducer.setNamesrvAddr(this.properties.getNameServerAddr());
        defaultMQProducer.setVipChannelEnabled(false);
        defaultMQProducer.setMaxMessageSize(this.properties.getProducer().getMaxMessageSize());
        defaultMQProducer.setSendMsgTimeout(this.properties.getProducer().getSendMsgTimeOut());
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(this.properties.getProducer().getRetryTimesWhenSendFailed());

    }


    @Override
    @PostConstruct
    public void start() {
        try {
            if (this.started.compareAndSet(false, true)) {
                this.defaultMQProducer.start();
            }
        } catch (Exception var2) {
            throw new MessageException(var2.getMessage());
        }
    }

    @Override
    @PreDestroy
    public void shutdown() {
        if (this.started.compareAndSet(true, false)) {
            this.defaultMQProducer.shutdown();
        }
    }

    @Override
    public SendResult send(Message message) {
        try {
            message.setTopic(message.getTopic()+"-"+properties.getEnv());
            return this.defaultMQProducer.send(message);
        } catch (MQClientException e) {
            throw new MessageException(e.getErrorMessage());
        } catch (RemotingException | InterruptedException e) {
            throw new MessageException(e.getMessage());
        } catch (MQBrokerException e) {
            throw new MessageException(e.getErrorMessage());
        }
    }

    @Override
    public void sendOneway(Message message) {
        try {
            this.defaultMQProducer.sendOneway(message);
        } catch (MQClientException e) {
            throw new MessageException(e.getErrorMessage());
        } catch (RemotingException | InterruptedException e) {
            throw new MessageException(e.getMessage());
        }
    }

    @Override
    public void sendAsync(Message var1, SendCallback var2) {
        try {
            this.defaultMQProducer.send(var1,var2);
        } catch (MQClientException e) {
            throw new MessageException(e.getErrorMessage());
        } catch (RemotingException | InterruptedException e) {
            throw new MessageException(e.getMessage());
        }
    }

    @Override
    public void setCallbackExecutor(ExecutorService callbackExecutor) {
        this.defaultMQProducer.setCallbackExecutor(callbackExecutor);
    }

    @Override
    public boolean isStarted() {
        return this.started.get();
    }

    @Override
    public boolean isClosed() {
        return !this.isStarted();
    }
}
