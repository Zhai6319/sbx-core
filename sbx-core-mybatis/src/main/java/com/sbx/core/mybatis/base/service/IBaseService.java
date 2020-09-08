package com.sbx.core.mybatis.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sbx.core.mybatis.base.domain.BaseDO;

/**
 * <p>IBaseService class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/21
 */
public interface IBaseService<T extends BaseDO> extends IService<T> {

}
