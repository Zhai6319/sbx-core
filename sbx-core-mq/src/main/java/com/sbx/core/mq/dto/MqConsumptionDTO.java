package com.sbx.core.mq.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.sbx.core.tool.util.Charsets;
import lombok.Data;
import org.springframework.util.DigestUtils;

@Data
@TableName(value = "mq_consumption")
public class MqConsumptionDTO {

    @TableId(value = "mq_key",type = IdType.INPUT)
    private String mqKey;

    private String processType;

    private String businessKey;

    public MqConsumptionDTO(String processType,String businessKey){
        this.mqKey = DigestUtils.md5DigestAsHex((processType+businessKey).getBytes(Charsets.UTF_8));
        this.processType = processType;
        this.businessKey = businessKey;
    }
}
