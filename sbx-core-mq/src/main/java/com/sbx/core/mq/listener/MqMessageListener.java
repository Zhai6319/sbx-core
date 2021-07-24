package com.sbx.core.mq.listener;


import com.alibaba.fastjson.JSONObject;
import com.sbx.core.mq.consumer.ConsumerRegister;
import com.sbx.core.mq.dto.MqConsumptionDTO;
import com.sbx.core.mq.processor.MessageProcessor;
import com.sbx.core.mq.processor.ProcessHelper;
import com.sbx.core.tool.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class MqMessageListener implements MessageListenerConcurrently {

    @Resource
    private ProcessHelper processHelper;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        ConsumeConcurrentlyStatus status = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        for (MessageExt messageExt : list) {
            List<MessageProcessor> processors = ConsumerRegister.findByTopicAndTag(messageExt.getTopic(),messageExt.getTags());
            if (CollectionUtil.isEmpty(processors)) {
                break;
            }
            String bodyStr = new String(messageExt.getBody());
            for (MessageProcessor processor : processors) {
                MqConsumptionDTO mqConsumptionDTO = null;
                try {
                    Object obj = JSONObject.parseObject(bodyStr, processor.getEntityClass());
                    if (processor.getIdempotent()) {
                        mqConsumptionDTO = processHelper.isIdempotent(obj, messageExt, processor);
                    }
                    if (Objects.nonNull(mqConsumptionDTO)) {
                        processor.process(obj);
                    }
                } catch (Exception e) {
                    log.error("consume error, message={}, body={}", messageExt, new String(messageExt.getBody()), e);
                    status = ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    // 如果幂等了，但是业务处理失败，需要删除记录
                    if (Objects.nonNull(mqConsumptionDTO) && StringUtils.isNotBlank(mqConsumptionDTO.getMqKey())) {
                        processHelper.deleteByKey(mqConsumptionDTO.getMqKey());
                    }
                }
            }
        }
        return status;
    }
}
