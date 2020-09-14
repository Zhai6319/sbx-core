package com.sbx.core.tool.excel.processor;

import java.util.List;

/**
 * <p>说明:</p>
 * @author Z.jc
 * @since 2020/9/10
 * @version 1.0.0
 *
 * */
public abstract class AbstractGeneralTreatmentProcessor<T> {

    /**
     * 一般处理excel导入执行器
     * @param dataList 导入数据
     */
    public abstract void doExecute(List<T> dataList);


}
