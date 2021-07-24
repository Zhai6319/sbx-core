package com.sbx.core.mq.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "rocketmq")
public class MqConfigProperties {

    private String nameServerAddr;

    private String groupName;

    private String env;

    private Consumer consumer;

    private Producer producer;

    @Data
    public static class Consumer{
        // 消费者线程数据量
        private List<String> topics;
        private String isOnOff;
        private Integer threadMin;
        private Integer threadMax;
        private Integer messageBatchMaxSize;
    }

    @Data
    public static class Producer{
        private String isOnOff;
        // 消息最大值
        private Integer maxMessageSize;
        // 消息发送超时时间
        private Integer sendMsgTimeOut;
        // 失败重试次数
        private Integer retryTimesWhenSendFailed;
    }



}
