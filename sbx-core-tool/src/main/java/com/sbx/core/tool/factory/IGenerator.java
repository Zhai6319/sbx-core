package com.sbx.core.tool.factory;

import com.sbx.core.model.base.result.PageResult;

import java.util.List;
import java.util.Set;

/**
 * @author zhaijianchao
 */
public interface IGenerator {

    /**
     * 单个对象的深度复制及类型转换，response/domain , po
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     * @author zhaijianchao
     * @date  2018年8月6日
     */
    <T, S> T convert(S s, Class<T> clz);

    /**
     * list深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     * @author zhaijianchao
     * @date  2018年8月6日
     */
    <T, S> List<T> convert(List<S> s, Class<T> clz);

    /**
     * set深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     * @author zhaijianchao
     * @date  2018年8月6日
     */
    <T, S> Set<T> convert(Set<S> s, Class<T> clz);

    /**
     * page深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     * @author zhaijianchao
     * @date  2018年8月6日
     */
    <T, S> PageResult<T> convert(PageResult<S> s, Class<T> clz);

    /**
     * 数组深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     * @author zhaijianchao
     * @date  2018年8月6日
     */
    <T, S> T[] convert(S[] s, Class<T> clz);



}
