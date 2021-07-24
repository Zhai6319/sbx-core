package com.sbx.core.mq.api;

import org.apache.rocketmq.client.consumer.listener.MessageListener;


public interface Consumer {

    boolean isStarted();

    boolean isClosed();

    void start();

    void shutdown();

    void subscribe(String var1, String var2, MessageListener var3);

    void unsubscribe(String var1);
}
