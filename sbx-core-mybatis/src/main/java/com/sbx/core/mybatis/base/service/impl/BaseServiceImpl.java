package com.sbx.core.mybatis.base.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sbx.core.mybatis.base.domain.BaseDO;
import com.sbx.core.mybatis.base.mapper.IBaseMapper;
import com.sbx.core.mybatis.base.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>BaseServiceImpl class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/21
 */
@Validated
public class BaseServiceImpl<M extends IBaseMapper<T>, T extends BaseDO> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T entity) {
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setIsDeleted(false);
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(T entity) {
        entity.setUpdateTime(LocalDateTime.now());
        return super.updateById(entity);
    }
}
