package com.sbx.core.web.log.aop;

import com.sbx.core.web.log.AbstractLogExecute;
import com.sbx.core.web.log.annotation.Log;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/1/12
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private AbstractLogExecute abstractLogExecute;


    @Around("@annotation(apiOperation)")
    public Object around(ProceedingJoinPoint point, ApiOperation apiOperation) throws Throwable {
        // 发送异步日志事件
        long beginTime = System.currentTimeMillis();
        abstractLogExecute.prefilter(point,apiOperation);
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //记录日志
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        Method method = methodSignature.getMethod();
        Log log = method.getAnnotation(Log.class);
        abstractLogExecute.logExecute(point,result,apiOperation,log,time);
        return result;
    }



}
