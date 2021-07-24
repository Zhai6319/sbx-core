package com.sbx.core.tool.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.sbx.core.tool.excel.processor.AbstractGeneralTreatmentProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/9/10
 */
public class GeneralTreatmentListener<T,S extends AbstractGeneralTreatmentProcessor<T>> extends AnalysisEventListener<T> {

    private final List<T> list = new ArrayList<>();

    private final S processor;

    private final int batchCount;



    public GeneralTreatmentListener(S processor){
        this.processor = processor;
        this.batchCount = 10;

    }

    public GeneralTreatmentListener(S processor,int batchCount){
        this.processor = processor;
        this.batchCount = Math.max(batchCount, 5);
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        list.add(t);
        if (list.size() >= batchCount) {
            processor.doExecute(list);
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        processor.doExecute(list);
        list.clear();
        processor.doAfterAllAnalysed();
    }
}
