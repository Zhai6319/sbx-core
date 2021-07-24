package com.sbx.core.web.log;

import com.sbx.core.web.EnableLog;
import com.sbx.core.web.log.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/12/30
 */
@Component
@ConditionalOnMissingBean(annotation = {EnableLog.class})
public class DefaultLogExecuteBean extends AbstractLogExecute {

    @Override
    public void logExecute(ProceedingJoinPoint point, Object result, ApiOperation apiOperation, Log log, Long time) {

    }

    @Override
    public void prefilter(ProceedingJoinPoint point, ApiOperation apiOperation) {

    }
}
