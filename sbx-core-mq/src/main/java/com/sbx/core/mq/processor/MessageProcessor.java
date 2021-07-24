package com.sbx.core.mq.processor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.lang.reflect.ParameterizedType;

@Slf4j
public abstract class MessageProcessor<T> {

    @Getter
    private Class<T> entityClass;





    public MessageProcessor(){
        entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract String getTopic();

    public abstract String getTag();

    public abstract boolean getIdempotent();

    /**
     * 获取业务消息的唯一key，用于保证消息消费幂等
     *
     * @param data
     * @param message
     * @return
     */
    public String getBusinessKey(T data, MessageExt message) {
        return message.getMsgId();
    }

    public MessageModel getMessageModel(){
        return MessageModel.CLUSTERING;
    }

    public abstract void process(T data) throws Exception;




}
