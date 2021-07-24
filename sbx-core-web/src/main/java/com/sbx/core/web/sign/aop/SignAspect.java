package com.sbx.core.web.sign.aop;

import com.sbx.core.web.sign.AbstractSignVerifyExecute;
import com.sbx.core.web.sign.annotation.Sign;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
public class SignAspect {

    @Resource
    private AbstractSignVerifyExecute abstractSignVerifyExecute;


    @Pointcut("@annotation(sign)")
    public void serviceStatistics(Sign sign) {
    }


    @Before("serviceStatistics(sign)")
    public void doBefore(JoinPoint joinPoint, Sign sign) {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        abstractSignVerifyExecute.verifySign(joinPoint,request);
    }

}
