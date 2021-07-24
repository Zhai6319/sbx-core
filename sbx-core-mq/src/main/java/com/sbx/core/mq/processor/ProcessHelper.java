package com.sbx.core.mq.processor;


import com.alibaba.fastjson.JSON;
import com.sbx.core.mq.dto.MqConsumptionDTO;
import com.sbx.core.mq.service.MqConsumptionService;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: yekai
 * @date: 2019/1/5 14:41
 * @description:
 */
@Service
public class ProcessHelper {
    private static final Logger logger = LoggerFactory.getLogger(ProcessHelper.class);

    @Resource
    private MqConsumptionService mqConsumptionService;

    /**
     * 判断消息是否幂等
     ***/
    public MqConsumptionDTO isIdempotent(Object data, MessageExt message, MessageProcessor processor) {
        String processType = processor.getClass().getName();
        String ss = "$$";
        if (processType.contains(ss)) {
            processType = processType.substring(0, processType.indexOf(ss));
        }
        String businessKey = processor.getBusinessKey(data, message);
        MqConsumptionDTO mqConsumptionDTO = this.buildMqConsumptionDto(processType, businessKey);
        try {
            mqConsumptionService.insert(mqConsumptionDTO);
            return mqConsumptionDTO;
        } catch (DuplicateKeyException e1) {
            logger.warn("catch exception isIdempotent 主键 冲突，该消息已经处理 dto={}", JSON.toJSONString(mqConsumptionDTO));
            return null;
        } catch (Exception e2) {
            logger.error("catch exception 插入数据库异常 dto={}", JSON.toJSONString(mqConsumptionDTO), e2);
            throw new RuntimeException(e2);
        }
    }

    public Boolean deleteByKey(String mqKey){
        return mqConsumptionService.removeById(mqKey);
    }

    /**
     * 转成业务对应的dto
     *
     * @param messageBody
     * @param sourceClassName
     * @return
     * @throws ClassNotFoundException
     */
    public Object getBusinessData(String messageBody, String sourceClassName) throws ClassNotFoundException {
        return com.alibaba.fastjson.JSON.parseObject(messageBody, Class.forName(sourceClassName));
    }

    /**
     * 创建
     *
     * @param processType
     * @param businessKey
     * @return
     */
    private MqConsumptionDTO buildMqConsumptionDto(String processType, String businessKey) {
        return new MqConsumptionDTO(processType, businessKey);
    }

}
