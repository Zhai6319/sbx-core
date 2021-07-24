package com.sbx.core.web.log;

import com.sbx.core.web.log.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/12/29
 */
public abstract class AbstractLogExecute {

    public abstract void logExecute(ProceedingJoinPoint point, Object result,ApiOperation apiOperation,Log log, Long time);


    public abstract void prefilter(ProceedingJoinPoint point, ApiOperation apiOperation);
}
