package com.sbx.core.mq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sbx.core.mq.dto.MqConsumptionDTO;

public interface MqConsumptionService extends IService<MqConsumptionDTO> {

    int insert(MqConsumptionDTO dto);

}
