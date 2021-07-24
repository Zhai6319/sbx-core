package com.sbx.core.mq.api;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.ExecutorService;

public interface Producer {

    /**
     * 开启消费
     */
    void start();

    /**
     * 关闭消费
     */
    void shutdown();

    /**
     * 发送消息
     * @param var1  消息信息
     * @return      返回发送结果
     */
    SendResult send(Message var1);

    /**
     * 发送单项消息
     * @param var1  消息信息
     */
    void sendOneway(Message var1);

    /**
     * 发送同步消息
     * @param var1  消息信息
     * @param var2  发送回调处理
     */
    void sendAsync(Message var1, SendCallback var2);

    /**
     *  设置回调执行器
     * @param var1 回调执行器
     */
    void setCallbackExecutor(ExecutorService var1);

    /**
     * 是否开启
     * @return
     */
    boolean isStarted();

    /**
     * 是否关闭
     * @return
     */
    boolean isClosed();


}
