package com.sbx.core.mybatis.base.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sbx.core.mybatis.base.entity.BaseEntity;
import com.sbx.core.mybatis.base.mapper.IBaseMapper;
import com.sbx.core.mybatis.base.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

/**
 * <p>BaseServiceImpl class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/21
 */
@Validated
public class BaseServiceImpl<M extends IBaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T entity) {
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setIsDeleted(false);
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(T entity) {
        entity.setUpdateTime(new Date());
        return super.updateById(entity);
    }
}
