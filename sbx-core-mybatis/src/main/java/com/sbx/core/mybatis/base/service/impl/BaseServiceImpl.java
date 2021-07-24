package com.sbx.core.mybatis.base.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sbx.core.mybatis.base.dataobject.BaseDO;
import com.sbx.core.mybatis.base.mapper.IBaseMapper;
import com.sbx.core.mybatis.base.service.IBaseService;
import org.springframework.validation.annotation.Validated;

/**
 * <p>BaseServiceImpl class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/21
 */
@Validated
public class BaseServiceImpl<M extends IBaseMapper<T>, T extends BaseDO> extends ServiceImpl<M, T> implements IBaseService<T> {

}
