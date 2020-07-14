package com.sbx.core.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * <p>SbxLogicSqlInjector class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/21
 */
public class SbxLogicSqlInjector extends DefaultSqlInjector {
    /**
     * 如果只需增加方法，保留MP自带方法
     * 可以super.getMethodList() 再add
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        return methodList;
    }


}
