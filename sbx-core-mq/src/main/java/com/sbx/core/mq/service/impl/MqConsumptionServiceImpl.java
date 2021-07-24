package com.sbx.core.mq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sbx.core.mq.dto.MqConsumptionDTO;
import com.sbx.core.mq.mapper.MqConsumptionMapper;
import com.sbx.core.mq.service.MqConsumptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqConsumptionServiceImpl extends ServiceImpl<MqConsumptionMapper, MqConsumptionDTO> implements MqConsumptionService {

    @Override
    public int insert(MqConsumptionDTO dto){
        return baseMapper.insert(dto);
    }

}
