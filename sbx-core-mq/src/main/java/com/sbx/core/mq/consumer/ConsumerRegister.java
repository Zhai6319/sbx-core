package com.sbx.core.mq.consumer;

import com.sbx.core.mq.processor.MessageProcessor;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class ConsumerRegister {

    /**
     * 执行器容器
     */
    private static final  Map<String, List<MessageProcessor>> EXECUTOR_MAP = new HashMap<>();

    private static final Map<String, List<String>> TAG_MAP = new HashMap<>();

    /**
     * 注册
     *
     * @param topic 主题
     * @param processor 处理器
     */
    public static <T> void register(String topic,String tag, MessageProcessor<T> processor) {
        topic = topic.split("-")[0];
        List<MessageProcessor> processors = EXECUTOR_MAP.get(topic+"_"+tag);
        if (Objects.isNull(processors)){
            processors = new ArrayList<>();
        }
        processors.add(processor);
        EXECUTOR_MAP.put(topic+"_"+tag, processors);
        List<String> tags = TAG_MAP.get(topic);
        if (Objects.isNull(tags)) {
            tags = new ArrayList<>();
        }
        tags.add(tag);
        TAG_MAP.put(topic,tags);
    }

    /**
     * 查找执行器
     *
     * @param topic 主题
     * @return 消息处理器
     */
    public static List<MessageProcessor> findByTopicAndTag(String topic,String tag) {
        topic = topic.split("-")[0];
        return EXECUTOR_MAP.get(topic+"_"+tag);
    }

    /**
     * 获取消息tag
     * @param topic 主题
     * @return      返回tags
     */
    public static String findTagsStrByTopic(String topic){
        topic = topic.split("-")[0];
        List<String> tags = TAG_MAP.get(topic);
        if (CollectionUtils.isEmpty(tags)) {
            return null;
        }
        StringBuilder tagsStr = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            if (i == tags.size() - 1) {
                tagsStr.append(tags.get(i));
            } else {
                tagsStr.append(tags.get(i)).append("||");
            }
        }
        return tagsStr.toString();
    }

}
